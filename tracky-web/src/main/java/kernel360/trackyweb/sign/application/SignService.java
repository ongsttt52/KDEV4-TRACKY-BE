package kernel360.trackyweb.sign.application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.trackycore.core.common.api.ApiResponse;
import kernel360.trackycore.core.common.api.PageResponse;
import kernel360.trackycore.core.domain.entity.BizEntity;
import kernel360.trackycore.core.domain.entity.MemberEntity;
import kernel360.trackyweb.biz.domain.provider.BizDomainProvider;
import kernel360.trackyweb.sign.application.dto.request.ApproveRequest;
import kernel360.trackyweb.sign.application.dto.request.MemberDeleteRequest;
import kernel360.trackyweb.sign.application.dto.request.MemberSearchByFilter;
import kernel360.trackyweb.sign.application.dto.request.MemberUpdateRequest;
import kernel360.trackyweb.sign.application.dto.request.SignupRequest;
import kernel360.trackyweb.sign.application.dto.response.MemberResponse;
import kernel360.trackyweb.sign.application.validation.SignValidator;
import kernel360.trackyweb.sign.domain.provider.MemberProvider;
import kernel360.trackyweb.sign.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {

	private final MemberProvider memberProvider;
	private final BizDomainProvider bizDomainProvider;
	private final JwtTokenProvider jwtTokenProvider;
	private final SignValidator signValidator;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 계정 검색
	 * @param memberSearchByFilter
	 * @return ApiResponse<List<MemberResponse>>
	 */
	@Transactional(readOnly = true)
	public ApiResponse<List<MemberResponse>> getMembersBySearchFilter(MemberSearchByFilter memberSearchByFilter) {

		Page<MemberEntity> members = memberProvider.getMembersBySearchFilter(
			memberSearchByFilter.search(),
			memberSearchByFilter.toPageable()
		);

		Page<MemberResponse> memberResponses = members.map(MemberResponse::from);
		PageResponse pageResponse = PageResponse.from(memberResponses);

		return ApiResponse.success(memberResponses.getContent(), pageResponse);
	}

	/**
	 * 가입 신청
	 * @param signupRequest
	 */
	@Transactional
	public void signup(SignupRequest signupRequest) {

		signValidator.validateSignup(signupRequest.memberId(), signupRequest.bizRegNum());

		BizEntity biz = BizEntity.create(
			signupRequest.bizName(),
			UUID.randomUUID().toString(),
			signupRequest.bizRegNum(),
			signupRequest.bizAdmin(),
			signupRequest.bizPhoneNum(),
			null
		);
		bizDomainProvider.save(biz);

		MemberEntity member = MemberEntity.create(
			biz,
			signupRequest.memberId(),
			passwordEncoder.encode(signupRequest.pwd()),
			signupRequest.email(),
			"USER",
			"wait"
		);

		memberProvider.save(member);
	}

	/**
	 *	아이디 중복 확인
	 */
	@Transactional(readOnly = true)
	public Boolean isExistMemberId(String memberId) {
		log.info("Check attempt for isExistMemberId");

		return memberProvider.existsByMemberId(memberId);
	}

	/**
	 * 가입 신청 list
	 * @return List<MemberEntity>
	 */
	@Transactional(readOnly = true)
	public List<MemberEntity> getApproveList() {
		log.info("Approve attempt for getAppoveList");

		return memberProvider.findByStatus("wait");
	}

	/**
	 * 계정 삭제
	 * @param memberDeleteRequest
	 * @return MemberEntity
	 */
	@Transactional
	public MemberEntity delete(MemberDeleteRequest memberDeleteRequest) {
		log.info("Delete for memberId: {}", memberDeleteRequest.memberId());

		MemberEntity member = memberProvider.getMember(memberDeleteRequest.memberId());

		member.delete();

		memberProvider.save(member);

		return member;
	}

	/**
	 * 계정 수정
	 * @param memberUpdateRequest
	 * @return MemberEntity
	 */
	@Transactional
	public MemberEntity update(MemberUpdateRequest memberUpdateRequest) {
		log.info("Update for memberId: {}", memberUpdateRequest.memberId());

		MemberEntity member = memberProvider.getMember(memberUpdateRequest.memberId());

		member.update(
			memberUpdateRequest.bizName(),
			memberUpdateRequest.bizRegNum(),
			memberUpdateRequest.bizAdmin(),
			memberUpdateRequest.bizPhoneNum(),
			memberUpdateRequest.email(),
			memberUpdateRequest.role(),
			memberUpdateRequest.status()
		);

		memberProvider.save(member);

		return member;
	}

	/**
	 * 계정 상태 변경
	 * @param approveRequest
	 * @return MemberEntity
	 */
	@Transactional
	public MemberEntity updateStatus(ApproveRequest approveRequest) {

		log.info("Update status for memberId: {}", approveRequest.memberId());

		MemberEntity member = memberProvider.getMember(approveRequest.memberId());

		member.updateStatus(approveRequest.status());

		memberProvider.save(member);

		return member;
	}

	/**
	 * 주어진 memberId와 평문 비밀번호를 통해 회원 인증 처리.
	 */
	@Transactional
	public MemberEntity authenticate(String memberId, String pwd) {

		log.info("Login attempt for memberId: {}", memberId);

		MemberEntity member = memberProvider.getMember(memberId);

		signValidator.validateStatus(member);
		signValidator.validatePassword(pwd, member);

		return member;
	}

	@Transactional
	public String generateJwtToken(MemberEntity member) {
		String bizName = member.getBiz().getBizName();
		Long bizId = member.getBiz().getId();
		String bizUuid = member.getBiz().getBizUuid();

		return jwtTokenProvider.generateToken(member.getMemberId(), member.getRole(), bizName, bizId, bizUuid);
	}
}
