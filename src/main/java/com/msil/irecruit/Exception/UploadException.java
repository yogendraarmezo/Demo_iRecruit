package com.msil.irecruit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UploadException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UploadException(String message) {
        super(message);
    }
	public UploadException(String message, Throwable cause) {
        super(message, cause);
    
	}	

}
