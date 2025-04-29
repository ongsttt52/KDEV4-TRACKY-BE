package kernel360.trackyemulator.application.service.dto.response;

public record MdnBizResponse(
	String mdn,
	long bizId
) {

	public static MdnBizResponse of(String mdn, long bizId) {
		return new MdnBizResponse(mdn, bizId);
	}
}
