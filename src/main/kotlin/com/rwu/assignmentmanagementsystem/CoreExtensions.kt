package com.rwu.assignmentmanagementsystem

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Any.createSlf4jLogger(): Logger = LoggerFactory.getLogger(this.javaClass.name)