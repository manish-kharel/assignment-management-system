package com.rwu.assignmentmanagementsystem.userprofile.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table
data class Assignment(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int,
  val professorId: Int,

  val file: String,

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "assignment_faculty",
    joinColumns = [JoinColumn(name = "assignment_id")],
    inverseJoinColumns = [JoinColumn(name = "faculty_id")]
  )
  var faculties: List<Faculty> = mutableListOf(),
)