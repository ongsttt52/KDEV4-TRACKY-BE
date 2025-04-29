package kernel360trackybe.trackyhub.application.dto.response;

public record MdnBizResponse(
	String mdn,
	long bizId
) {

	public static MdnBizResponse of(String mdn, long bizId) {
		return new MdnBizResponse(mdn, bizId);
	}
}
