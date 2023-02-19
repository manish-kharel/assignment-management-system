package com.rwu.assignmentmanagementsystem.userprofile.interfaces

import com.rwu.assignmentmanagementsystem.userprofile.application.AssignmentService
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AssignmentController(
  private val assignmentService: AssignmentService
) {

  @PostMapping("/createAssignment")
  fun createAssignment(
    @RequestBody assignment: Assignment
  ): Assignment = assignmentService.createAssignment(assignment)
}