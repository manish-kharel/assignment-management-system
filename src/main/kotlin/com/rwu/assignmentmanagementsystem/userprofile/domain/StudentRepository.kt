package com.rwu.assignmentmanagementsystem.userprofile.domain

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Int> {

  fun findByEmail(email: String): Student?
  fun findAllByFaculty(faculty: Faculty): List<Student>
}