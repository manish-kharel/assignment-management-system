package com.rwu.assignmentmanagementsystem.userprofile.interfaces

import com.rwu.assignmentmanagementsystem.userprofile.application.UserAccountService
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Professor
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Student
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class UserController(
  val userService: UserAccountService
) {

  @PostMapping("/createStudent")
  fun createStudent(
    @RequestBody student: Student
  ) = userService.createStudent(student)

  @PostMapping("/createProfessor")
  fun createProfessor(
    @RequestBody professor: Professor
  ) = userService.createProfessor(professor)

  @GetMapping("getProfessor")
  fun getProfessor(
    @PathVariable id: Int
  ) = userService.getProfessor(id)

  @GetMapping("getAllProfessors")
  fun getAllProfessors(
  ) = userService.getAllProfessors()

  @GetMapping("getStudent")
  fun getStudent(
    @PathVariable id : Int
  ) = userService.getStudent(id)

  @GetMapping("getAllStudents")
  fun getAllStudents(
  ) = userService.getAllStudents()
}