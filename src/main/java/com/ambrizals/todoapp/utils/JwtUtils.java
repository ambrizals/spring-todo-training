package com.ambrizals.todoapp.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.ambrizals.todoapp.entities.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * JWT Utility
 * 
 * @author ambrizals
 * @author koushikkothagal
 *
 */
@Service
public class JwtUtils {
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<Object> invalidToken(SignatureException e) {
		Map<String, String> errors = new HashMap<>();
		errors.put("status", HttpStatus.UNAUTHORIZED.getReasonPhrase());
		errors.put("message", e.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
	}	

	private String SECRET_KEY = "Siap Bos";
	
	
	/**
	 * To get data from token client
	 * 
	 * @param String token
	 * @return Claims
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	/**
	 * To extract token with Jwt Method
	 * 
	 * @param <T>
	 * @param String token
	 * @param Function<Claims, T> claimsResolver
	 * @return
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extract user data
	 * 
	 * 
	 * @return Claims
	 */
	public Claims extractUserData() {
		String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		Claims claims = extractAllClaims(token);
		claims.remove("sub");
		claims.remove("iat");
		return claims;
	}

	public String extractUser(String token) {
		String username = extractClaim(token, Claims::getSubject);
		return username;
	}
		
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	/**
	 * Generate a token
	 * 
	 * @param User user
	 * @return String claims
	 */
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("id", user.getId());
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());

		return createToken(claims, user.getUsername());
	}
	
	public Boolean validateToken(String token) {
		Claims claims = extractAllClaims(token);
		if(claims != null) {
			return true;
		} else {
			return false;
		}
	}
}
