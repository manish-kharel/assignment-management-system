package com.rwu.assignmentmanagementsystem.userprofile.application

import com.rwu.assignmentmanagementsystem.userprofile.domain.AssignmentRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.FacultyRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Assignment
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import org.springframework.stereotype.Component

@Component
class AssignmentService(
  private val assignmentRepository: AssignmentRepository,
  private val facultyRepository: FacultyRepository
) {
  fun createAssignment(assignment: Assignment): Assignment {
    val mappedFaculties = assignment.faculties.mapNotNull { faculty ->
      mapFacultyWithCorrectId(faculty)
    }
   return assignmentRepository.save(
      assignment.copy(
        faculties = mappedFaculties
      )
    )
  }

  private fun mapFacultyWithCorrectId(faculty: Faculty) =
    facultyRepository.findBySemesterAndFacultyName(faculty.semester, faculty.facultyName)

}