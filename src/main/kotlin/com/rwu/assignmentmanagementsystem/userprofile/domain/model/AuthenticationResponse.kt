package com.rwu.assignmentmanagementsystem.userprofile.domain.model

class AuthenticationResponse(
  val valid : Boolean,
  val role: String?,
  val id: Int?,
)