package com.both.testing_pilot_backend.jwt;

import com.both.testing_pilot_backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtService handles the creation, parsing, and validation of JWT tokens
 * using a symmetric secret key. It is responsible for extracting claims
 * from the token and ensuring the token is valid and not expired.
 */
@Component
public class JwtService {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	public static final String SECRET = "FVPr6Q/fVlHGZkElZubC0Zaxv657dPUfDQ4o9DADjSin7+uST1d2A5klMWrMK8fmSl3doyf2wn5zj56VC+qqCg==";

	private String createToken(Map<String, Object> claim, String subject) {
		return Jwts.builder()
						.claims(claim)
						.subject(subject)
						.issuedAt(new Date(System.currentTimeMillis()))
						.expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
						.signWith(getSignKey()).compact();
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Base64.getDecoder().decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	//2. generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		User user = (User) userDetails;
		claims.put("user_id", user.getUserId());
		return createToken(claims, user.getEmail());
	}

	//3. retrieving any information from token we will need the secret key
	private Claims extractAllClaim(String token) {
		return Jwts.parser()
						.verifyWith(getSignKey())
						.build()
						.parseSignedClaims(token)
						.getPayload();
	}

	//4. extract a specific claim from the JWT token’s claims.
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaim(token);
		return claimsResolver.apply(claims);
	}

	//5. retrieve username from jwt token
	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	//6. retrieve expiration date from jwt token
	public Date extractExpirationDate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	//7. check expired token
	private Boolean isTokenExpired(String token) {
		return extractExpirationDate(token).before(new Date());
	}

	//8. validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String email = extractEmail(token);
		return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
