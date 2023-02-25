package com.rwu.assignmentmanagementsystem.userprofile.interfaces.converter

import com.rwu.assignmentmanagementsystem.FRONTEND_DATE_FORMAT
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.FacultyName
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Submission
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.Student
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionResponse
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.FacultyName as InterfaceFacultyName
import org.springframework.stereotype.Component

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
      fileName = submitted.fileName,
      assignmentId = submitted.assignment.id,
      assignmentName = submitted.assignment.file,
      comment = submitted.comment,
      student = Student(
        studentId = submitted.student.id,
        name = submitted.student.name,
        faculty = Faculty(
          facultyName = convertFacultyNameDtoToInterface(submitted.student.faculty.facultyName),
          semesterNumber = submitted.student.faculty.id ?: 0,
        )
      ),
      submittedOn = submitted.submittedOn?.format(FRONTEND_DATE_FORMAT)
    )
}