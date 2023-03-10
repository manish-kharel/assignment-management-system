package com.rwu.assignmentmanagementsystem.userprofile.interfaces

import com.rwu.assignmentmanagementsystem.createSlf4jLogger
import com.rwu.assignmentmanagementsystem.userprofile.application.AssignmentService
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.converter.AssignmentConverter
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.AssignmentRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionRequest
import com.rwu.assignmentmanagementsystem.userprofile.interfaces.model.SubmissionResponse
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin
class AssignmentController(
  private val assignmentService: AssignmentService,
  private val assignmentConverter: AssignmentConverter
) {
  val logger = createSlf4jLogger()

  //For professors page
  // 1. to upload assignment
  @PostMapping(
    "/createAssignment", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE]
  )
  fun createAssignment(
    @RequestPart file: MultipartFile,
    @RequestPart assignmentRequest: AssignmentRequest,
  ): AssignmentRequest {
    logger.info(assignmentRequest.fileName + "  file received")
    return assignmentService.createAssignment(assignmentRequest, file).let {
      assignmentConverter.convertAssignmentDtoToInterface(it)
    }
  }

  // 2. to get Assignment current status for professor,
  @GetMapping("getAssignmentStatus/{professorId}")
  fun getAssignmentStatus(
    @PathVariable professorId: Int
  ) = assignmentService.getAssignmentStatus(professorId)

  //  for students page
//  1. to get to-do assignments
  @GetMapping("getAssignmentsForFaculty/{facultyId}/{studentId}")
  fun getAssignmentsForFaculty(
    @PathVariable facultyId: Int,
    @PathVariable studentId: Int,
  ) = assignmentService.getAssignmentsForFaculty(facultyId, studentId)

  @GetMapping("downloadAssignmentFile/download/{id}")
  fun downloadAssignmentFile(
    @PathVariable id: Int
  ): ResponseEntity<ByteArray> {
    val responseBody = assignmentService.downloadAssignmentFile(id).let {
      assignmentConverter.getFileFromByteArray(it)
    }
    val headers = HttpHeaders().apply {
      ContentDisposition.builder("attachment").build()
    }
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).headers(headers)
      .body(responseBody)
  }

  // 2. to create assignment submission for student
  @PostMapping("createSubmission")
  fun createSubmission(
    @RequestBody submissionRequest: SubmissionRequest
  ): SubmissionResponse = assignmentService.createSubmissionForStudent(submissionRequest).let { submitted ->
    assignmentConverter.convertSubmissionDtoToInterface(submitted)
  }

  // 3 Get students who have submitted
  @GetMapping("getSubmissionsByAssignment/{assignmentId}")
  fun getSubmissionsByAssignment(
    @PathVariable assignmentId: Int,
  ): List<SubmissionResponse> {
    return assignmentService.getSubmissionsByAssignment(assignmentId).map { submitted ->
      assignmentConverter.convertSubmissionDtoToInterface(submitted)
    }
  }

  // Get student's specific submission based on Assignment ID
  @GetMapping("findSubmissionByStudentAndAssignmentId/{assignmentId}/{studentId}")
  fun getSubmissionByStudentAndAssignmentId(
    @PathVariable studentId: Int,
    @PathVariable assignmentId: Int,
  ): SubmissionResponse {
    return assignmentService.getSubmissionByStudentIdAndAssignmentId(studentId, assignmentId).let { submitted ->
      assignmentConverter.convertSubmissionDtoToInterface(submitted)
    }
  }
}