package kernel360.trackyweb.member.infrastructure.security.filter;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel360.trackyweb.member.infrastructure.security.jwt.JwtTokenProvider;
import kernel360.trackyweb.member.infrastructure.security.validation.JwtValidation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtValidation jwtValidation;
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	/**
	 * 요청마다 실행되며, Authorization 헤더에서 JWT 토큰을 추출해 유효성을 검사.
	 * 유효한 토큰이면 SecurityContext에 인증 정보를 설정함.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {

		if (jwtValidation.checkSseEvent(request, response, filterChain)) {
			return;
		}

		if (jwtValidation.checkAlbHealthCheck(request, response, filterChain)) {
			return;
		}

		String token = jwtValidation.resolveToken(request);

		if (jwtValidation.isValidToken(token)) {
			String memberId = jwtTokenProvider.getMemberId(token);
			String role = jwtTokenProvider.getRole(token).toUpperCase();
			String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;

			UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
					memberId,
					null,
					Collections.singletonList(new SimpleGrantedAuthority(authority))
				);

			// SecurityContext에 Authentication 객체를 설정
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}
