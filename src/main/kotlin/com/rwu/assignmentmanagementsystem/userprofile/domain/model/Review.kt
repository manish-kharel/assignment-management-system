package com.rwu.assignmentmanagementsystem.userprofile.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Table
@Entity
data class Review(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Int? = null ,
  val review: String?,
  val grade: Int?,

  @OneToOne
  @JoinColumn(name = "submission_id")
  val submission: Submission?
)