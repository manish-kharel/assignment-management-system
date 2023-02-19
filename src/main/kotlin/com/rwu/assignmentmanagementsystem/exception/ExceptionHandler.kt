package com.rwu.assignmentmanagementsystem.exception

import com.rwu.assignmentmanagementsystem.createSlf4jLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

  val logger = createSlf4jLogger()

  @ExceptionHandler
  fun handleInvalidRequestBodyException(ex: java.lang.Exception): ResponseEntity<String> {
    logger.error(ex.message)
    return when (ex) {
      is DatabaseErrorException -> {
        ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
      }
      is UserAlreadyExistsException -> {
        ResponseEntity(ex.message, HttpStatus.CONFLICT)
      }
      is UserDoesNotExistException ->
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
      else -> ResponseEntity("arunai kunai exception", HttpStatus.BAD_REQUEST)
    }
  }
}