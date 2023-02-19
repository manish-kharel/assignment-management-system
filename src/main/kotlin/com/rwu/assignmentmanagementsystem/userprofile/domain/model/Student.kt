package com.rwu.assignmentmanagementsystem.userprofile.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table
@Entity
data class Student(

  @Id
  @Column(name = "id", unique = true, updatable = false)
  val id: Int,
  val name: String,
  val email: String,
  val password: String,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "faculty_id")
  var faculty: Faculty,
)