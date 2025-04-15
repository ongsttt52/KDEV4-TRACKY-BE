package kernel360.trackyweb.member.infrastructure.security.validation;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel360.trackyweb.member.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtValidation {

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * HTTP Authorization 헤더에서 "Bearer " 접두어를 제거하고 토큰만 반환.
	 *
	 * @param request HTTP 요청 객체
	 * @return JWT 토큰 문자열 또는 null
	 */
	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");

		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		} else {
			return null;
		}
	}

	public boolean isValidToken(String token) {
		return token != null && jwtTokenProvider.validateToken(token);
	}

	/**
	 * SSE 요청은 토큰 없이 통과시킴
	 * @param request
	 * @return boolean
	 */
	public boolean checkSseEvent(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws
		ServletException,
		IOException {
		if (request.getRequestURI().startsWith("/events")) {
			filterChain.doFilter(request, response);
			return true;
		}
		return false;
	}

	/**
	 * ALB HealthCheck 허용
	 * @param request
	 * @return boolean
	 */
	public boolean checkAlbHealthCheck(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws
		ServletException,
		IOException {
		if (request.getRequestURI().startsWith("/actuator")) {
			filterChain.doFilter(request, response);
			return true;
		}
		return false;
	}

}
