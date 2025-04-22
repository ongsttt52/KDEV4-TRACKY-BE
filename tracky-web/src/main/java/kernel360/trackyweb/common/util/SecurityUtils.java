package kernel360.trackyweb.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static String getBizUuid() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//추후 공통 예외 처리로 리팩토링
		if (auth == null) {
			throw new IllegalStateException("SecurityContext에 인증 정보가 없습니다.");
		}

		Object credentials = auth.getCredentials();
		if (!(credentials instanceof String bizUuid)) {
			throw new IllegalStateException("인증 정보에 bizUuid가 없습니다.");
		}

		return bizUuid;
	}
}
