package com.rwu.assignmentmanagementsystem.userprofile.application.converter

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Student
import com.rwu.assignmentmanagementsystem.userprofile.application.model.Student as StudentDto
import org.springframework.stereotype.Component

@Component
class UserProfileConverter {

  fun convertStudentDomainToDto(students: List<Student>): List<StudentDto> = students.map { student ->
    StudentDto(
      id = student.id,
      name = student.name
    )
  }
}