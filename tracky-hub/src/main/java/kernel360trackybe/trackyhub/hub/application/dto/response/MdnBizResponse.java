package kernel360trackybe.trackyhub.hub.application.dto.response;

public record MdnBizResponse(
	String mdn,
	long bizId
) {

	public static MdnBizResponse of(String mdn, long bizId) {
		return new MdnBizResponse(mdn, bizId);
	}
}
