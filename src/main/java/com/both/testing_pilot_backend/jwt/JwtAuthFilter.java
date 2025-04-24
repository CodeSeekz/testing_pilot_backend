package com.both.testing_pilot_backend.jwt;

import com.both.testing_pilot_backend.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter is a custom filter that processes incoming HTTP requests and authenticates users based on JWT tokens.
 * It ensures the request contains a valid token, validates it, and then populates the Spring SecurityContext
 * with the corresponding authenticated user.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserServiceImpl userService;

	/**
	 * This method intercepts each HTTP request to:
	 * 1. Extract the JWT from the Authorization header.
	 * 2. Validate the token.
	 * 3. If valid, authenticate the user by setting their details in the SecurityContext.
	 *
	 * @param request     The incoming HTTP request
	 * @param response    The HTTP response
	 * @param filterChain The filter chain that continues the request processing
	 * @throws ServletException In case of servlet-related errors
	 * @throws IOException      In case of I/O errors
	 */
	@Override
	protected void doFilterInternal(
					@NonNull HttpServletRequest request,
					@NonNull HttpServletResponse response,
					@NonNull FilterChain filterChain
	) throws ServletException, IOException {

		// Extract the "Authorization" header from the request
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String email = null;

		// Check if the header is present and starts with "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			// Extract the token by removing "Bearer " prefix
			token = authHeader.substring(7);

			// Decode the email (subject) from the token
			email = jwtService.extractEmail(token);
		}

		// Authenticate the user only if email exists and no one is authenticated yet
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Load user details (e.g., password, roles) from the user service
			UserDetails userDetails = userService.loadUserByUsername(email);

			// Validate the token to ensure it's not expired and matches the user
			if (jwtService.validateToken(token, userDetails)) {

				// Create an authentication token containing user credentials and authorities (roles)
				UsernamePasswordAuthenticationToken authenticationToken =
								new UsernamePasswordAuthenticationToken(
												userDetails,
												null,
												userDetails.getAuthorities()
								);

				// Attach request-specific information (IP, session) to the authentication details
				authenticationToken.setDetails(
								new WebAuthenticationDetailsSource().buildDetails(request)
				);

				// Mark the user as authenticated by setting the token in the security context
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		// Continue with the remaining filters and eventually reach the controller
		filterChain.doFilter(request, response);
	}
}
