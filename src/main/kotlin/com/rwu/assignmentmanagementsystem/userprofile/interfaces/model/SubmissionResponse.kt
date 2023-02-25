package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model

class SubmissionResponse(
  val fileName: String,
  val assignmentId: Int,
  val assignmentName: String,
  val studentName: String,
  val submittedOn: String,
)