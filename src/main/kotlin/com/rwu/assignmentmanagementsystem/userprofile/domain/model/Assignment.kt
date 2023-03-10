package com.rwu.assignmentmanagementsystem.userprofile.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.Lob
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table
data class Assignment(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int? = null,
  val professorId: Int,

  val fileName: String,

  val uploaded: LocalDate?,
  val deadline: LocalDateTime?,

  @Lob
  @Column(length = 1000)
  val file: ByteArray? = null,

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "assignment_faculty",
    joinColumns = [JoinColumn(name = "assignment_id")],
    inverseJoinColumns = [JoinColumn(name = "faculty_id")]
  )
  var faculties: List<Faculty> = mutableListOf(),
)