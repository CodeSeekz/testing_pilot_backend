package com.both.testing_pilot_backend.exceptions;


public class BadRequestException extends RuntimeException {
	public BadRequestException(String message) {
		super(message);
	}
}
