package kernel360.trackyweb.member.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private String secretKey = "jiwonKey";

	private final long expiration = 1000L * 60 * 60 * 24; // 1day

	public String generateToken(String memberId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.setSubject(memberId)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
			.compact();
	}

	public String extractMemberId(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			extractMemberId(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
