package com.pegasus.ams.mgmt.exception.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends RuntimeException {
    private HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

}
