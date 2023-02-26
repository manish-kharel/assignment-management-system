package com.rwu.assignmentmanagementsystem.userprofile.application.model

import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.AssignmentRequest

data class AssignmentStatus(
  val assignmentRequest: AssignmentRequest,
  val totalStudents: Int,
  val totalSubmissions: Int,
  val totalReviewsWritten: Int,
  val students: List<Student>,
  val graded: Boolean = (totalStudents == totalSubmissions && totalSubmissions == totalReviewsWritten)
//  val uploaded : LocalDateTime
//  val deadline : LocalDateTime
//  val graded: Boolean
)