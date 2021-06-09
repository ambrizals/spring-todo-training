package com.ambrizals.todoapp.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.ambrizals.todoapp.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT Utility
 * 
 * @author ambrizals
 * @author koushikkothagal
 *
 */
@Service
public class JwtUtils {
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

	public User extractUser(String json) {
		User user = User.fromJson(json);
		return user;
	}
	
	// public String extractUsername(String token) {
	// 	return extractClaim(token, Claims::getSubject);
	// }
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userDetails.getUsername());
	}
	
	public Boolean validateToken(String token) {
		if(!isTokenExpired(token)) {
			return true;
		} else {
			return false;
		}
	}
}
