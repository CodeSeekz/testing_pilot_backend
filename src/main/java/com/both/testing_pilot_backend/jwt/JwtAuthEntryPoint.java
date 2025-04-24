package com.both.testing_pilot_backend.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtAuthEntryPoint handles unauthorized access attempts to secured REST endpoints.
 * It implements Spring Security's AuthenticationEntryPoint and is used to return
 * a 401 Unauthorized response when a request is made without valid authentication.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

	/**
	 * This method is triggered anytime an unauthenticated user requests a secured HTTP resource.
	 * Instead of redirecting to a login page, it returns a 401 status with a custom message.
	 *
	 * @param request       The HTTP request that triggered the exception
	 * @param response      The HTTP response where the error should be written
	 * @param authException The exception thrown when authentication fails
	 * @throws IOException if sending the error fails
	 */
	@Override
	public void commence(HttpServletRequest request,
	                     HttpServletResponse response,
	                     AuthenticationException authException) throws IOException {

		// Send HTTP 401 Unauthorized response with a message
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
