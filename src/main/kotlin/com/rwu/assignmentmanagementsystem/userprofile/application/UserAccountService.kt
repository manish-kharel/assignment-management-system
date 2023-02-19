package com.rwu.assignmentmanagementsystem.userprofile.application

import com.rwu.assignmentmanagementsystem.createSlf4jLogger
import com.rwu.assignmentmanagementsystem.exception.UserAlreadyExistsException
import com.rwu.assignmentmanagementsystem.userprofile.domain.FacultyRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.ProfessorRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.StudentRepository
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Faculty
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Professor
import com.rwu.assignmentmanagementsystem.userprofile.domain.model.Student
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserAccountService(
  val studentRepository: StudentRepository,
  val facultyRepository: FacultyRepository,
  val professorRepository: ProfessorRepository
) {

  val logger = createSlf4jLogger()

  fun createStudent(student: Student): Student {
    if (studentRepository.findById(student.id).isEmpty  &&
      studentRepository.findByEmail(student.email) == null) {
      val newStudent: Student
      val faculty = facultyRepository.findBySemesterAndFacultyName(
        student.faculty.semester, student.faculty.facultyName
      )
      newStudent = if (faculty == null) {
        facultyRepository.save(student.faculty)
        student
      } else {
        student.copy(faculty = faculty)
      }
      return studentRepository.save(newStudent)
    } else {
      logger.info("The user already has an account")
      throw UserAlreadyExistsException("The user already has an account")
    }
  }

  fun createProfessor(professor: Professor): Professor {

    if (professorRepository.findById(professor.id).isEmpty &&
      professorRepository.findByUniversityEmail(professor.universityEmail) == null
    ) {
      val newProfessor: Professor
      val newFaculties = mutableSetOf<Faculty>()
      professor.faculties.map { faculty ->
        val savedFaculty = facultyRepository.findBySemesterAndFacultyName(faculty.semester, faculty.facultyName)
        if (savedFaculty == null) {
          newFaculties.add(facultyRepository.save(faculty))
        } else {
          newFaculties.add(savedFaculty)
        }
      }
      newProfessor = professor.copy(faculties = newFaculties)
      return professorRepository.save(newProfessor)
    } else {
      logger.info("The user already has an account")
      throw UserAlreadyExistsException("The user already has an account")
    }
  }

  fun getProfessor(id: Int): Optional<Professor> = professorRepository.findById(id)
  fun getAllProfessors(): MutableList<Professor> = professorRepository.findAll()
  fun getAllStudents(): MutableList<Student> = studentRepository.findAll()
  fun getStudent(id: Int): Optional<Student> = studentRepository.findById(id)

}