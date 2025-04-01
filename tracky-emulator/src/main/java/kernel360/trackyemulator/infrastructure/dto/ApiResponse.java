package kernel360.trackyemulator.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ApiResponse {
	private String rstCd;	// 결과 코드
	private String rstMsg;	// 결과 메시지
	private String mdn;		// 차량 번호
	private String token;	// Token
}
