package kernel360.trackyweb.sign.presentation;

import java.util.List;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackyweb.sign.application.SignService;
import kernel360.trackyweb.sign.application.dto.request.ApproveRequest;
import kernel360.trackyweb.sign.application.dto.request.LoginRequest;
import kernel360.trackyweb.sign.application.dto.request.MemberDeleteRequest;
import kernel360.trackyweb.sign.application.dto.request.MemberSearchByFilter;
import kernel360.trackyweb.sign.application.dto.request.MemberUpdateRequest;
import kernel360.trackyweb.sign.application.dto.request.SignupRequest;
import kernel360.trackyweb.sign.application.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignController {

	private final SignService signService;

	@GetMapping("/members")
	public ApiResponse<List<MemberResponse>> getMembers(
		@ModelAttribute MemberSearchByFilter memberSearchByFilter
	) {
		return signService.getMembersBySearchFilter(memberSearchByFilter);
	}

	@PostMapping("/members")
	public ApiResponse<MemberResponse> updateMembers(
		@RequestBody @Valid MemberUpdateRequest memberUpdateRequest
	) {
		MemberResponse result = MemberResponse.from(signService.update(memberUpdateRequest));
		return ApiResponse.success(result);
	}

	@DeleteMapping("/members")
	public ApiResponse<MemberResponse> deleteMembers(
		@RequestBody @Valid MemberDeleteRequest memberDeleteRequest
	) {
		MemberResponse result = MemberResponse.from(signService.delete(memberDeleteRequest));
		return ApiResponse.success(result);
	}

	@GetMapping("/signup/{memberId}")
	public ApiResponse<Boolean> checkExistId(
		@PathVariable String memberId
	) {
		Boolean result = signService.isExistMemberId(memberId);
		return ApiResponse.success(result);
	}

	@GetMapping("/approves")
	public ApiResponse<List<MemberResponse>> getApprove() {

		List<MemberResponse> result = MemberResponse.fromList(signService.getApproveList());
		return ApiResponse.success(result);
	}

	@PostMapping("/approves")
	public ApiResponse<MemberResponse> approve(
		@RequestBody @Valid ApproveRequest approveRequest
	) {
		MemberResponse result =  MemberResponse.from(signService.updateStatus(approveRequest, "active"));
		return ApiResponse.success(result);
	}

	@PostMapping("/reject")
	public ApiResponse<MemberResponse> reject(
		@RequestBody @Valid ApproveRequest approveRequest
	) {
		MemberResponse result = MemberResponse.from(signService.updateStatus(approveRequest, "deactive"));
		return ApiResponse.success(result);
	}


	@PostMapping("/signup")
	public ApiResponse<String> signup(@RequestBody @Valid SignupRequest request) {
		signService.signup(request);
		return ApiResponse.success("회원가입이 완료되었습니다.");
	}

	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody @Valid LoginRequest request) {
		// DB에서 회원 조회 및 비밀번호 검증 (비밀번호 불일치, 존재하지 않는 회원일 경우 예외 발생)
		MemberEntity member = signService.authenticate(request.memberId(), request.pwd());

		String jwtToken = signService.generateJwtToken(member);
		log.info("Login success for memberId: {}", request.memberId());

		return ApiResponse.success(jwtToken);
	}
}
