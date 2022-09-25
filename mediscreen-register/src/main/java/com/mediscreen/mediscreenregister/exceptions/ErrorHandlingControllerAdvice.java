package com.mediscreen.mediscreenregister.exceptions;

import com.mediscreen.mediscreenregister.controllers.PatientInfoApiController;
import com.mediscreen.mediscreenregister.controllers.PatientInfoController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    private Logger log = LogManager.getLogger(PatientInfoApiController.class);

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Parameter is empty" + e);
        return new ResponseEntity<String>("Parameter is empty", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> handleNoSuchElementExeption(NoSuchElementException e) {
        log.error("Element not found" + e);
        return new ResponseEntity<String>("Element not found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FullNameException.class)
    ResponseEntity<String> handleFullNameException(FullNameException e) {
        log.error("Full name is required" + e);
        return new ResponseEntity<String>("Full name is required", HttpStatus.BAD_REQUEST);
    }
}
