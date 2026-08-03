package com.shumkar.helpdesksystem.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(
			ResourceNotFoundException exception,
			HttpServletRequest request
	) {
		ApiErrorResponse response = new ApiErrorResponse(
				Instant.now(),
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(),
				"RESOURCE_NOT_FOUND",
				exception.getMessage(),
				request.getRequestURI()
		);

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicate(
			DuplicateResourceException exception,
			HttpServletRequest request
	) {
		ApiErrorResponse response = new ApiErrorResponse(
				Instant.now(),
				HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.getReasonPhrase(),
				"DUPLICATE_RESOURCE",
				exception.getMessage(),
				request.getRequestURI()
		);

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}
}
