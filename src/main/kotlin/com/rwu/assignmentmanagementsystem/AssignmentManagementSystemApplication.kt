package com.rwu.assignmentmanagementsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class AssignmentManagementSystemApplication

fun main(args: Array<String>) {
  runApplication<AssignmentManagementSystemApplication>(*args)
}


