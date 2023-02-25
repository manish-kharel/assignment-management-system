package com.rwu.assignmentmanagementsystem.userprofile.application

import com.rwu.assignmentmanagementsystem.FRONTEND_DATE_FORMAT
import com.rwu.assignmentmanagementsystem.userprofile.application.converter.AssignmentConverter
import com.rwu.assignmentmanagementsystem.userprofile.application.converter.UserProfileConverter
import com.rwu.assignmentmanagementsystem.userprofile.application.model.AssignmentStatus
import com.rwu.assignmentmanagementsystem.userprofile.domain.AssignmentRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.FacultyRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.StudentRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.SubmissionRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Submission
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.AssignmentRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import com.rwu.assignmentmanagementsystem.userprofile.application.model.Student as StudentDto

@Component
class AssignmentService(
  private val assignmentRepository: AssignmentRepository,
  private val facultyRepository: FacultyRepository,
  private val studentRepository: StudentRepository,
  private val submissionRepository: SubmissionRepository,
  private val userProfileConverter: UserProfileConverter,
  private val assignmentConverter: AssignmentConverter,
) {
  fun createAssignment(assignmentRequest: AssignmentRequest): Assignment {
    val assignment = assignmentConverter.convertAssignmentRequestToDomain(assignmentRequest)
    val mappedFaculties = assignment.faculties.mapNotNull { faculty ->
      mapFacultyWithCorrectId(faculty)
    }

    return assignmentRepository.save(
      assignment.copy(
        faculties = mappedFaculties,
      )
    )
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
        assignment = assignment, totalStudents = allStudentsDto.size, students = allStudentsDto
      )
    })
    return assignmentStatuses
  }

  fun getAssignmentsForFaculty(facultyId: Int): List<Assignment> {
    val faculty = facultyRepository.findById(facultyId)
    return assignmentRepository.findAllByFacultiesContaining(faculty)
  }

  fun createSubmissionForStudent(submissionRequest: SubmissionRequest): Submission {
    return submissionRepository.save(
      Submission(
        comment = submissionRequest.comment,
        fileName = submissionRequest.fileName,
        submittedOn = LocalDateTime.parse(
          submissionRequest.submittedOn, FRONTEND_DATE_FORMAT
        ),
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
    )
  }
}