package com.rwu.assignmentmanagementsystem.userprofile.interfaces.model

data class SubmissionResponse(
  val comment : String,
  val fileName: String,
  val assignmentId: Int,
  val assignmentName: String,
  val student: Student,
  val submittedOn: String?,
)