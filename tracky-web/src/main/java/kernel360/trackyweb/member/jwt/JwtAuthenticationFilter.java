package kernel360.trackyweb.member.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kernel360.trackyweb.member.application.service.MemberDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberDetailsService memberDetailsService;

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, MemberDetailsService memberDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.memberDetailsService = memberDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
		throws ServletException, IOException{

		String header = request.getHeader("Authorization");
		if ( header != null && header.startsWith("Bearer")) {
			String token = header.substring(7);
			if (jwtTokenProvider.isTokenValid(token)) {
				String memberId = jwtTokenProvider.extractMemberId(token);
				UserDetails userDetails = memberDetailsService.loadUserByUsername(memberId);
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request,response);
	}

}
