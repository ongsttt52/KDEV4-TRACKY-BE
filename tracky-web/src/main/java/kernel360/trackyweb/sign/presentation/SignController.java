package kernel360.trackyweb.sign.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackyweb.sign.application.SignService;
import kernel360.trackyweb.sign.application.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignController {

	private final SignService signService;

	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody LoginRequest request) {
		// DB에서 회원 조회 및 비밀번호 검증 (비밀번호 불일치, 존재하지 않는 회원일 경우 예외 발생)
		MemberEntity member = signService.authenticate(request.memberId(), request.pwd());

		String jwtToken = signService.generateJwtToken(member);
		log.info("Login success for memberId: {}", request.memberId());

		return ApiResponse.success(jwtToken);
	}
}
