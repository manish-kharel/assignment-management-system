package com.rwu.assignmentmanagementsystem.userprofile.interfaces

import com.rwu.assignmentmanagementsystem.userprofile.application.AuthenticationService
import com.rwu.assignmentmanagementsystem.userprofile.application.model.AuthenticationResponse
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.LoginDetail
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin
class AuthenticationController(
  private val authenticationService: AuthenticationService
) {

  @PostMapping("/getAuthentication")
  fun getAuthentication(@RequestBody loginDetail: LoginDetail): AuthenticationResponse =
    authenticationService.getAuthentication(loginDetail.email, loginDetail.password)
}


