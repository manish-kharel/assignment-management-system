package com.rwu.assignmentmanagementsystem.userprofile.domain

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Int> {
  fun findBySubmissionId(submissionId: Int) : Review?
  fun countReviewsBySubmission_Assignment(assignment: Assignment) : Int
}
