package com.rwu.assignmentmanagementsystem.userprofile.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Faculty(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int? = null,

  @Enumerated(EnumType.STRING)
  val facultyName: FacultyName,
  val semester: Int,
)


