package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model

data class SubmissionRequest(
  val fileName: String,
  val comment: String,
  val studentId: Int,
  val assignmentId: Int,
  val submittedOn: String?,
)
