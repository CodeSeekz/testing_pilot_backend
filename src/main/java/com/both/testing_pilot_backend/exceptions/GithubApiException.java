package com.both.testing_pilot_backend.exceptions;

public class GithubApiException extends RuntimeException {
    public GithubApiException(String message) {
        super(message);
    }
}
