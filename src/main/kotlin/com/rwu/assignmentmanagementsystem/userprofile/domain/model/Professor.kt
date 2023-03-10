package com.rwu.assignmentmanagementsystem.userprofile.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Table
@Entity
data class Professor(
  @Id
  @Column(name = "id", unique = true, updatable = false)
  val id: Int,
  val firstName: String,
  val lastName: String,
  val universityEmail: String,
  val password: String,

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "professor_faculty",
    joinColumns = [JoinColumn(name = "professor_id")],
    inverseJoinColumns = [JoinColumn(name = "faculty_id")]
  )
  var faculties: MutableSet<Faculty> = mutableSetOf(),
)