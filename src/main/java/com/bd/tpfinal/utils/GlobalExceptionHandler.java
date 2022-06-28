package com.bd.tpfinal.utils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.hibernate.StaleObjectStateException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, Object> body = new HashMap<>();
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		
		body.put("errors", errors);
		
		return new ResponseEntity<>(body,headers, status);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleConstraintValidationException(ConstraintViolationException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseEntity<String> handleNullPointerException (NullPointerException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.NOT_FOUND);
			
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleEmptyResultDataAccessException (EmptyResultDataAccessException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoSuchElementException.class)	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleNoSuchElementException (NoSuchElementException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(StaleObjectStateException.class)	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleStaleObjectStateException (StaleObjectStateException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IndexOutOfBoundsException.class)	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleIndexOutOfBoundsException (IndexOutOfBoundsException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DeliveryException.class)	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleDeliveryException (DeliveryException ex){
		return new ResponseEntity<>("Error: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}

