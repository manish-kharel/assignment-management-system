package com.rwu.assignmentmanagementsystem.userprofile.application.converter

import com.rwu.assignmentmanagementsystem.FRONTEND_DATE_FORMAT
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.FacultyName
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.AssignmentRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.FacultyRequest
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class AssignmentConverter {
  fun convertAssignmentRequestToDomain(assignmentRequest: AssignmentRequest): Assignment = Assignment(
    id = assignmentRequest.id,
    professorId = assignmentRequest.professorId,
    fileName = assignmentRequest.fileName,
    uploaded = LocalDate.now(),
    deadline = LocalDateTime.parse(assignmentRequest.deadline, FRONTEND_DATE_FORMAT),
    faculties = assignmentRequest.faculties.map { faculty ->
      convertFacultyRequestToDomain(faculty)
    }
  )

  fun convertFacultyRequestToDomain(faculty: FacultyRequest) =
    Faculty(
      id = null,
      semester = faculty.semester,
      facultyName = getEnumForFacultyName(faculty.facultyName)
    )

  private fun getEnumForFacultyName(facultyName: String): FacultyName = when (facultyName) {
    "ELECTRICAL_ENGINEERING" -> FacultyName.ELECTRICAL_ENGINEERING
    "E_MOBILITY" -> FacultyName.E_MOBILITY
    "INFORMATION_TECHNOLOGY" -> FacultyName.INFORMATION_TECHNOLOGY
    else -> FacultyName.UNSPECIFIED
  }
}