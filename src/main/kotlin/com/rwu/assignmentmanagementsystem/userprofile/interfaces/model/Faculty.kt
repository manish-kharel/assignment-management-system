package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model


data class Faculty(
  val facultyName: FacultyName,
  val semesterNumber : Int,
)

enum class FacultyName {
  ELECTRICAL_ENGINEERING,
  INFORMATION_TECHNOLOGY,
  E_MOBILITY,
  UNSPECIFIED
}