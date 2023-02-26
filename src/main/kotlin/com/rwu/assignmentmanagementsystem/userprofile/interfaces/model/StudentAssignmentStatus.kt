package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model

import java.time.LocalDate
import java.time.LocalDateTime

data class StudentAssignmentStatus(
  val id: Int,
  val professorId: Int,
  val fileName: String,
  val downloadFileUri: String = "download/${id}",
  val uploaded: LocalDate?,
  val deadline: LocalDateTime?,
  val submitted: Boolean,
  val grade: Int?,
)