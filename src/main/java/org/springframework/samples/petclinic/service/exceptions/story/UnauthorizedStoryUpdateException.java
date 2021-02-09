package org.springframework.samples.petclinic.service.exceptions.story;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizedStoryUpdateException extends Exception{
	
    public UnauthorizedStoryUpdateException(String message) {
        super(message);
    }
	
}
