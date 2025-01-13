package com.example.crud.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// Handle UserNotFoundException
	@ExceptionHandler(UserNotFoundException.class)
	public ProblemDetail handleUserNotFoundException(UserNotFoundException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setTitle("User not found");
		problemDetail.setDetail(e.getMessage());
		return problemDetail;
	}

	// Handle validation errors
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setTitle("Bad Request");
		Map<String, String> validationErrors = new HashMap<>();
		for (FieldError e : ex.getFieldErrors()) {
			validationErrors.put(e.getField(), e.getDefaultMessage());
		}
		problemDetail.setDetail("Invalid Parameters " + validationErrors);
		return new ResponseEntity<Object>(problemDetail, HttpStatus.BAD_REQUEST);
	}

	// Handle NoHandlerFoundException (e.g., An inexistent controller uri)
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setTitle("Resource Not Found");
		problemDetail.setDetail("The server could not find the specified resource");
		return new ResponseEntity<Object>(problemDetail, HttpStatus.NOT_FOUND);
	}

	// Handle NoResourceFoundException (e.g., static content)
	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setTitle("Resource Not Found");
		problemDetail.setDetail("The server could not find the specified resource");
		return new ResponseEntity<Object>(problemDetail, HttpStatus.NOT_FOUND);
	}

	//Handle all remaining errors
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setTitle("Bad Request");
		problemDetail.setDetail("The server could not handle this request");
		return new ResponseEntity<Object>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
