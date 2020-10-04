package com.qa.springust.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BandNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

}
