package com.rwu.assignmentmanagementsystem.userprofile.domain

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AssignmentRepository : JpaRepository<Assignment, Int> {

  fun findAllByProfessorId(professorId: Int): List<Assignment>
  fun findAllByFacultiesContaining(faculty: Optional<Faculty>): List<Assignment>
}