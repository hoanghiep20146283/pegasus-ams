package com.pegasus.ams.mgmt.exception;

import com.pegasus.ams.mgmt.exception.error.ApplicationException;
import com.pegasus.ams.mgmt.exception.error.BadRequestException;
import com.pegasus.ams.mgmt.exception.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class ApplicationExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleCustomErrorExceptions(Exception e, HttpServletRequest httpServletRequest) {
        ApplicationException customErrorException = (ApplicationException) e;

        HttpStatus status = customErrorException.getStatus();

        return new ResponseEntity<>(
                new ApplicationExceptionResponse(status, customErrorException.getMessage(), httpServletRequest.getRequestURI()),
                status
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleCustomNotFoundExceptions(Exception e, HttpServletRequest httpServletRequest) {
        NotFoundException customNotFoundException = (NotFoundException) e;

        HttpStatus status = customNotFoundException.getStatus();

        return new ResponseEntity<>(
                new ApplicationExceptionResponse(status, customNotFoundException.getMessage(), httpServletRequest.getRequestURI()),
                status
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleCustomBadRequestExceptions(Exception e, HttpServletRequest httpServletRequest) {
        BadRequestException customBadRequestException = (BadRequestException) e;

        HttpStatus status = customBadRequestException.getStatus();

        return new ResponseEntity<>(
                new ApplicationExceptionResponse(status, customBadRequestException.getMessage(), httpServletRequest.getRequestURI()),
                status
        );
    }
}
