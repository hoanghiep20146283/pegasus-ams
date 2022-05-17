package com.pegasus.ams.mgmt.exception.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    private HttpStatus status = null;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(HttpStatus status, String message) {
        this(message);

        this.status = status;
    }

}
