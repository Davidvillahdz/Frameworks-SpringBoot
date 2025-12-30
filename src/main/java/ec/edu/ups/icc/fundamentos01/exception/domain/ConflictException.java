package ec.edu.ups.icc.fundamentos01.exception.domain;

import ec.edu.ups.icc.fundamentos01.exception.base.ApplicationException;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApplicationException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}