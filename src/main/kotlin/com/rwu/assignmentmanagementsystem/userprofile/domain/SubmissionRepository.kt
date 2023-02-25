package com.rwu.assignmentmanagementsystem.userprofile.domain

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Submission
import org.springframework.data.jpa.repository.JpaRepository

interface SubmissionRepository : JpaRepository<Submission, Int> {

  fun findSubmissionsByAssignment(assignment: Assignment): List<Submission>
}