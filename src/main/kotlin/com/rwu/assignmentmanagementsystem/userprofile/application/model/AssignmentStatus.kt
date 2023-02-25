package com.rwu.assignmentmanagementsystem.userprofile.application.model

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment

data class AssignmentStatus(
  val assignment: Assignment,
  val totalStudents: Int,
  val students: List<Student>
//  val uploaded : LocalDateTime
// val deadline : LocalDateTime
//  val graded: Boolean
)