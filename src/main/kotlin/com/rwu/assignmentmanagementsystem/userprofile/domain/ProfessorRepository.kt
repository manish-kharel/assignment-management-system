package com.rwu.assignmentmanagementsystem.userprofile.domain

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Professor
import org.springframework.data.jpa.repository.JpaRepository

interface ProfessorRepository : JpaRepository<Professor, Int> {

  fun findByUniversityEmail(email: String): Professor?
}