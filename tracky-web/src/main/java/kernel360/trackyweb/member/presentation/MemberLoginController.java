package kernel360.trackyweb.member.presentation;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackyweb.member.application.dto.LoginRequest;
import kernel360.trackyweb.member.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberLoginController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		UsernamePasswordAuthenticationToken token =
			new UsernamePasswordAuthenticationToken(request.getMemberId(), request.getPwd());

		Authentication authentication = authenticationManager.authenticate(token); // UserDetailsService 호출

		String jwt = jwtTokenProvider.generateToken(authentication.getName()); // ID 기준 토큰 발급
		return ResponseEntity.ok(Map.of("token", jwt));
	}
}


/*
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackyweb.member.application.dto.LoginRequest;
import kernel360.trackyweb.member.application.dto.LoginResponse;
import kernel360.trackyweb.member.jwt.JwtTokenProvider;

@RestController
@RequestMapping("")
public class MemberLoginController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	public MemberLoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getMemberId(), request.getPwd())
		);
		String token = jwtTokenProvider.generateToken(request.getMemberId());
		return ResponseEntity.ok(new LoginResponse(token));
	}

}
*/
