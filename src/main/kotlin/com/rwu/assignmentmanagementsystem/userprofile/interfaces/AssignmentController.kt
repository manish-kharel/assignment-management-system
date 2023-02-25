package com.rwu.assignmentmanagementsystem.userprofile.interfaces

import com.rwu.assignmentmanagementsystem.FRONTEND_DATE_FORMAT
import com.rwu.assignmentmanagementsystem.userprofile.application.AssignmentService
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.AssignmentRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class AssignmentController(
  private val assignmentService: AssignmentService
) {
  //For professors page
  // 1. to upload assignment
  @PostMapping("/createAssignment")
  fun createAssignment(
    @RequestBody assignmentRequest: AssignmentRequest
  ): Assignment = assignmentService.createAssignment(assignmentRequest)

  // 2. to get Assignment current status for professor,
  @GetMapping("getAssignmentStatus/{professorId}")
  fun getAssignmentStatus(
    @PathVariable professorId: Int
  ) = assignmentService.getAssignmentStatus(professorId)

  // for students page
  // 1. to get to-do assignments
  @GetMapping("getAssignmentsForFaculty/{facultyId}")
  fun getAssignmentsForFaculty(
    @PathVariable facultyId: Int
  ) = assignmentService.getAssignmentsForFaculty(facultyId)

  // 2. to create assignment submission for student
  @PostMapping("createSubmission")
  fun createSubmission(
    @RequestBody submissionRequest: SubmissionRequest
  ) = assignmentService.createSubmissionForStudent(submissionRequest).let { submitted ->
    SubmissionResponse(
      fileName = submitted.fileName,
      assignmentId = submitted.assignment.id,
      assignmentName = submitted.assignment.file,
      studentName = submitted.student.name,
      submittedOn = submitted.submittedOn.format(FRONTEND_DATE_FORMAT)
    )
  }

  // 3 Get students who have submitted
  @GetMapping("getSubmissionsByAssignment/{assignmentId}")
  fun getSubmissionsByAssignment(
    @PathVariable assignmentId: Int,
  ): List<SubmissionResponse> {
    return assignmentService.getSubmissionsByAssignment(assignmentId).map { submitted ->
      SubmissionResponse(
        fileName = submitted.fileName,
        assignmentId = submitted.assignment.id,
        assignmentName = submitted.assignment.file,
        studentName = submitted.student.name,
        submittedOn = submitted.submittedOn.format(FRONTEND_DATE_FORMAT)
      )
    }
  }
}