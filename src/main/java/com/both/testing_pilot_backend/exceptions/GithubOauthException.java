package com.both.testing_pilot_backend.exceptions;


public class GithubOauthException extends RuntimeException {
    public GithubOauthException(String message) {
        super(message);
    }
}
