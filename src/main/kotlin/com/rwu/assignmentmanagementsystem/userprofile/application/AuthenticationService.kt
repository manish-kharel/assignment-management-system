package com.rwu.assignmentmanagementsystem.userprofile.application

import com.rwu.assignmentmanagementsystem.exception.UserDoesNotExistException
import com.rwu.assignmentmanagementsystem.userprofile.domain.ProfessorRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.StudentRepository
import com.rwu.assignmentmanagementsystem.userprofile.application.model.AuthenticationResponse
import org.springframework.stereotype.Component

@Component
class AuthenticationService(
  private val studentRepository: StudentRepository,
  private val professorRepository: ProfessorRepository
) {

  fun getAuthentication(email: String, password: String): AuthenticationResponse {
    studentRepository.findByEmail(email)?.let { student ->
      return if (password == student.password) AuthenticationResponse(
        valid = true,
        role = student.javaClass.simpleName,
        id = student.id
      ) else AuthenticationResponse(
        valid = false,
        role = student.javaClass.simpleName,
        id = student.id
      )
    }
    professorRepository.findByUniversityEmail(email)?.let { professor ->
      return if (password == professor.password) AuthenticationResponse(
        valid = true,
        role = professor.javaClass.simpleName,
        id = professor.id
      )
      else AuthenticationResponse(
        valid = false,
        role = professor.javaClass.simpleName,
        id = professor.id
      )
    }
    throw UserDoesNotExistException("User with email ${email} doesn't exists")
  }
}
