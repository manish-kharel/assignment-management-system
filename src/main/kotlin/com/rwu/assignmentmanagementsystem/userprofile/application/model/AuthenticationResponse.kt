package com.rwu.assignmentmanagementsystem.userprofile.application.model

class AuthenticationResponse(
  val valid : Boolean,
  val role: String?,
  val id: Int?,
)