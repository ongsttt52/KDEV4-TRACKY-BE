package kernel360.trackyweb.member.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	// 생성자 주입
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * 요청마다 실행되며, Authorization 헤더에서 JWT 토큰을 추출해 유효성을 검사.
	 * 유효한 토큰이면 SecurityContext에 인증 정보를 설정함.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {

		String token = resolveToken(request);

		if (token != null && jwtTokenProvider.validateToken(token)) {
			String memberId = jwtTokenProvider.getMemberId(token);

			// 예시로 ROLE_USER 권한만 부여 (필요 시 토큰의 role 클레임 활용)
			UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
					memberId,
					null,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
				);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * HTTP Authorization 헤더에서 "Bearer " 접두어를 제거하고 토큰만 반환.
	 *
	 * @param request HTTP 요청 객체
	 * @return JWT 토큰 문자열 또는 null
	 */
	private String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}
}
