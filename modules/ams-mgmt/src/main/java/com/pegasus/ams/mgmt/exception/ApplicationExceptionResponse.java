package com.pegasus.ams.mgmt.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ApplicationExceptionResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private String path;

    public ApplicationExceptionResponse() {
        timestamp = new Date();
    }

    public ApplicationExceptionResponse(HttpStatus httpStatus, String message) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ApplicationExceptionResponse(HttpStatus httpStatus, String message, String path) {
        this(httpStatus, message);

        this.path = path;
    }
}
