package com.rwu.assignmentmanagementsystem.userprofile.interfaces

import com.rwu.assignmentmanagementsystem.userprofile.application.AuthenticationService
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.AuthenticationResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
  private val authenticationService: AuthenticationService
) {

  @GetMapping("/getAuthentication")
  fun getAuthentication(@RequestBody loginDetail: LoginDetail): AuthenticationResponse =
    authenticationService.getAuthentication(loginDetail.email, loginDetail.password)
}

class LoginDetail(
  val email: String,
  val password: String
)


