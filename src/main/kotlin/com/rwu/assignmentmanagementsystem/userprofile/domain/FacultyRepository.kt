package com.rwu.assignmentmanagementsystem.userprofile.domain

import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.FacultyName
import org.springframework.data.jpa.repository.JpaRepository

interface FacultyRepository : JpaRepository<Faculty, Int> {

  fun findBySemesterAndFacultyName(number: Int, facultyName: FacultyName) : Faculty?
}