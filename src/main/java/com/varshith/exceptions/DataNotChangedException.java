package com.varshith.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DataNotChangedException extends RuntimeException{
    public DataNotChangedException(String message){
        super(message);
    }
}
