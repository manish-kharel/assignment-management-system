package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model

import java.time.LocalDate

data class AssignmentRequest(
  val id: Int,
  val professorId: Int,
  val fileName: String,
  val downloadFileUri: String = "download/${id}",
  val uploaded: LocalDate?,
  val deadline: String?,
  var faculties: List<FacultyRequest> = mutableListOf()
)