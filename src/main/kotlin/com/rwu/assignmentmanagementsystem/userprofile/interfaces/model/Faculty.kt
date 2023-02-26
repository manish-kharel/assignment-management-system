package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model


data class Faculty(
  val facultyName: FacultyName,
  val semester : Int,
)

enum class FacultyName {
  ELECTRICAL_ENGINEERING,
  INFORMATION_TECHNOLOGY,
  E_MOBILITY,
  UNSPECIFIED
}