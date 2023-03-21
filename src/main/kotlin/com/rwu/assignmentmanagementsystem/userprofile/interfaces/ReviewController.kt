package com.rwu.assignmentmanagementsystem.userprofile.interfaces

import com.rwu.assignmentmanagementsystem.userprofile.application.AssignmentService
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.converter.AssignmentConverter
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.ReviewRequest
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class ReviewController(
  private val assignmentService: AssignmentService,
  private val assignmentConverter: AssignmentConverter
) {

  @PostMapping("/createReview")
  fun createReview(
    @RequestBody review: ReviewRequest
  ) = assignmentService.createReview(review)

  @GetMapping("/getReview/{submissionId}")
  fun getReview(
    @PathVariable submissionId: Int
  ) = assignmentService.getReviewBySubmissionId(submissionId).let {
    assignmentConverter.convertReviewToReviewRequest(it, submissionId)
  }
}