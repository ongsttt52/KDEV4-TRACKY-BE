package kernel360.trackyemulator.application.service.dto.response;

public record ApiResponse(
	String rstCd,    // 결과 코드
	String rstMsg,   // 결과 메시지
	String mdn,      // 차량 번호
	String token     // Token
) {
}
