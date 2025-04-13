package kernel360.trackyweb.member.presentation;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackyweb.member.application.dto.LoginRequest;
import kernel360.trackyweb.member.application.dto.LoginResponse;
import kernel360.trackyweb.member.application.service.MemberService;
import kernel360.trackyweb.member.domain.entity.MemberEntity;
import kernel360.trackyweb.member.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberLoginController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		log.info("Login attempt for memberId: {}", request.getMemberId());
		try {
			// DB에서 회원 조회 및 비밀번호 검증 (비밀번호 불일치, 존재하지 않는 회원일 경우 예외 발생)
			MemberEntity memberEntity = memberService.authenticate(request.getMemberId(), request.getPwd());
			String bizName = memberEntity.getBizId().getBizName();

			// 인증 성공 시 JWT 토큰 발급 (여기서는 role을 "admin"으로 고정)
			String jwt = jwtTokenProvider.generateToken(
				memberEntity.getMemberId(),
				memberEntity.getRole(),
				bizName);

			log.info("Login success for memberId: {}", request.getMemberId());
			return ResponseEntity.ok(new LoginResponse(jwt));
		} catch (IllegalArgumentException e) {
			// 인증 실패 시 커스텀 에러 메시지와 함께 401 반환
			log.error("Login failed for memberId: {} - {}", request.getMemberId(), e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("error", e.getMessage()));
		}
	}

	// Optional: 전역 혹은 컨트롤러 단위에서 발생하는 IllegalArgumentException 처리
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(Map.of("error", e.getMessage()));
	}
}
