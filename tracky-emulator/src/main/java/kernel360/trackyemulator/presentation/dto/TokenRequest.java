package kernel360.trackyemulator.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequest {
	private String mdn;    // 차량 번호
	private String tid;    // 터미널 아이디
	private String mid;    // 제조사 아이디
	private String pv;     // 패킷 버전
	private String did;    // 디바이스 아이디
}
