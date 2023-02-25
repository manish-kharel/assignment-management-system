package com.rwu.assignmentmanagementsystem.userprofile.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table
data class Submission(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int? = null,

  val comment: String,
  val fileName: String,

  val submittedOn: LocalDateTime,

  @OneToOne
  @JoinColumn(name = "student_id")
  val student: Student,

  @ManyToOne
  @JoinColumn(name = "assignment_id")
  val assignment: Assignment
)