package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model

import java.time.LocalDate

class AssignmentRequest(
  val id: Int,
  val professorId: Int,
  val file: String,
  val uploaded: LocalDate?,
  val deadline: String?,
  var faculties: List<FacultyRequest> = mutableListOf()
)