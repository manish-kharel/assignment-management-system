package com.rwu.assignmentmanagementsystem.userprofile.domain

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import org.springframework.data.jpa.repository.JpaRepository

interface AssignmentRepository : JpaRepository<Assignment, Int> {
}