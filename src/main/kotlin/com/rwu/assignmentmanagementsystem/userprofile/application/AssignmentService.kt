package com.rwu.assignmentmanagementsystem.userprofile.application

import com.rwu.assignmentmanagementsystem.FRONTEND_DATE_FORMAT
import com.rwu.assignmentmanagementsystem.FileUtils
import com.rwu.assignmentmanagementsystem.createSlf4jLogger
import com.rwu.assignmentmanagementsystem.userprofile.application.converter.AssignmentConverter
import com.rwu.assignmentmanagementsystem.userprofile.application.converter.UserProfileConverter
import com.rwu.assignmentmanagementsystem.userprofile.application.model.AssignmentStatus
import com.rwu.assignmentmanagementsystem.userprofile.domain.AssignmentRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.FacultyRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.ReviewRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.StudentRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.SubmissionRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Review
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Submission
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.AssignmentRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.ReviewRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.StudentAssignmentStatus
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionRequest
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import com.rwu.assignmentmanagementsystem.userprofile.application.model.Student as StudentDto
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.converter.AssignmentConverter as AssignmentConverterInterface

@Component
class AssignmentService(
  private val assignmentRepository: AssignmentRepository,
  private val facultyRepository: FacultyRepository,
  private val studentRepository: StudentRepository,
  private val submissionRepository: SubmissionRepository,
  private val reviewRepository: ReviewRepository,
  private val userProfileConverter: UserProfileConverter,
  private val assignmentConverter: AssignmentConverter,
  private val assignmentConverterInterface: AssignmentConverterInterface
) {
  fun createAssignment(assignmentRequest: AssignmentRequest, file: MultipartFile): Assignment {
    val logger = createSlf4jLogger()
    val assignment = assignmentConverter.convertAssignmentRequestToDomain(assignmentRequest)
    val mappedFaculties = assignment.faculties.mapNotNull { faculty ->
      mapFacultyWithCorrectId(faculty)
    }
    return assignmentRepository.save(
      assignment.copy(
        faculties = mappedFaculties,
        file = FileUtils.compressImage(file.bytes)
      )
    ).also {
      logger.info("file saved")
    }
  }

  private fun mapFacultyWithCorrectId(faculty: Faculty) =
    facultyRepository.findBySemesterAndFacultyName(faculty.semester, faculty.facultyName)

  fun getAssignmentStatus(professorId: Int): List<AssignmentStatus> {
    val assignmentStatuses = mutableListOf<AssignmentStatus>()
    val assignments = assignmentRepository.findAllByProfessorId(professorId)

    assignmentStatuses.addAll(assignments.map { assignment ->
      val allStudentsDto = mutableListOf<StudentDto>()
      assignment.faculties.forEach { eachFaculty ->
        val allStudents = studentRepository.findAllByFaculty(eachFaculty)
        userProfileConverter.convertStudentDomainToDto(allStudents).forEach { allStudentsDto.add(it) }
      }
      AssignmentStatus(
        assignmentRequest = assignmentConverterInterface.convertAssignmentDtoToInterface(assignment),
        totalStudents = allStudentsDto.size, students = allStudentsDto,
        totalSubmissions = submissionRepository.countSubmissionsByAssignmentId(assignment.id!!),
        totalReviewsWritten = reviewRepository.countReviewsBySubmission_Assignment(assignment)
      )
    })
    return assignmentStatuses
  }

  fun getAssignmentsForFaculty(facultyId: Int, studentId: Int): List<StudentAssignmentStatus> {
    val faculty = facultyRepository.findById(facultyId)
    return assignmentRepository.findAllByFacultiesContaining(faculty).map {
      val submission = submissionRepository.findSubmissionByStudentIdAndAssignmentId(studentId, it.id!!)
      assignmentConverterInterface.convertAssignmentDtoToStudentAssignmentStatus(it).copy(
        submitted = (submission != null),
        grade = reviewRepository.findBySubmissionId(submission?.id ?: 0)?.grade
      )
    }
  }

  fun createSubmissionForStudent(submissionRequest: SubmissionRequest): Submission {
    return submissionRepository.save(
      Submission(
        comment = submissionRequest.comment,
        fileName = submissionRequest.fileName,
        submittedOn = LocalDateTime.parse(LocalDateTime.now().format(FRONTEND_DATE_FORMAT), FRONTEND_DATE_FORMAT),
        student = studentRepository.findById(submissionRequest.studentId).get(),
        assignment = assignmentRepository.findById(submissionRequest.assignmentId).get()
      )
    )
  }

  fun getSubmissionsByAssignment(assignmentId: Int): List<Submission> {
    val assignment = assignmentRepository.findById(assignmentId).get()
    return submissionRepository.findSubmissionsByAssignment(assignment)
  }

  fun getSubmissionByStudentIdAndAssignmentId(studentId: Int, assignmentId: Int): Submission {
    return submissionRepository.findSubmissionByStudentIdAndAssignmentId(
      studentId = studentId,
      assignmentId = assignmentId
    )!!
  }

  fun createReview(req: ReviewRequest): Review = reviewRepository.save(
    Review(
      review = req.review,
      grade = req.grade,
      submission = submissionRepository.findById(req.submissionId).get(),
    )
  )

  fun getReviewBySubmissionId(submissionId: Int): Review? =
    reviewRepository.findBySubmissionId(submissionId)

  fun downloadAssignmentFile(id: Int): ByteArray =
    assignmentRepository.downloadFile(id) ?: byteArrayOf()

}
