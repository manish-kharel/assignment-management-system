package com.rwu.assignmentmanagementsystem.userprofile.interfaces.converter

import com.rwu.assignmentmanagementsystem.FRONTEND_DATE_FORMAT
import com.rwu.assignmentmanagementsystem.FileUtils
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.FacultyName
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Review
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Submission
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.AssignmentRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.FacultyRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.ReviewRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.Student
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.StudentAssignmentStatus
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionResponse
import org.springframework.stereotype.Component
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.FacultyName as InterfaceFacultyName

@Component("InterfaceAssignmentConverter")
class AssignmentConverter {

  fun convertFacultyNameDtoToInterface(facultyName: FacultyName): InterfaceFacultyName = when (facultyName) {
    FacultyName.ELECTRICAL_ENGINEERING -> InterfaceFacultyName.ELECTRICAL_ENGINEERING
    FacultyName.E_MOBILITY -> InterfaceFacultyName.E_MOBILITY
    FacultyName.INFORMATION_TECHNOLOGY -> InterfaceFacultyName.INFORMATION_TECHNOLOGY
    FacultyName.UNSPECIFIED -> InterfaceFacultyName.UNSPECIFIED
  }

  fun convertSubmissionDtoToInterface(submitted: Submission) =
    SubmissionResponse(
      id = submitted.id,
      fileName = submitted.fileName,
      assignmentId = submitted.assignment.id!!,
      assignmentName = submitted.assignment.fileName,
      comment = submitted.comment,
      student = Student(
        studentId = submitted.student.id,
        name = submitted.student.name,
        faculty = Faculty(
          facultyName = convertFacultyNameDtoToInterface(submitted.student.faculty.facultyName),
          semester = submitted.student.faculty.semester ?: 0,
        )
      ),
      submittedOn = submitted.submittedOn?.format(FRONTEND_DATE_FORMAT)
    )

  fun convertAssignmentDtoToInterface(assignment: Assignment): AssignmentRequest =
    AssignmentRequest(
      id = assignment.id!!,
      professorId = assignment.professorId,
      fileName = assignment.fileName,
      uploaded = assignment.uploaded,
      deadline = assignment.deadline?.format(FRONTEND_DATE_FORMAT),
      faculties = assignment.faculties.map {
        convertFacultyDtoToInterface(it)
      }
    )

  fun getFileFromByteArray(byteArray: ByteArray) =
    FileUtils.decompressImage(byteArray)


  private fun convertFacultyDtoToInterface(it: com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty): FacultyRequest =
    FacultyRequest(
      facultyName = it.facultyName.name,
      semester = it.semester
    )

  fun convertAssignmentDtoToStudentAssignmentStatus(assignment: Assignment): StudentAssignmentStatus =
    StudentAssignmentStatus(
      id = assignment.id!!,
      professorId = assignment.professorId,
      fileName = assignment.fileName,
      uploaded = assignment.uploaded,
      deadline = assignment.deadline,
      submitted = false,
      grade = null
    )

  fun convertReviewToReviewRequest(it: Review?, submissionId : Int): ReviewRequest = ReviewRequest(
    review = it?.review,
    grade = it?.grade,
    submissionId = submissionId
  )
}