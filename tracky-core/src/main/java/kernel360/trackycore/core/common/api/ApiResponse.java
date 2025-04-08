package kernel360.trackycore.core.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ApiResponse<T> {
	private int status;
	private String message;
	private T data;
	private PageResponse pageResponse;

	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.of(200, "success", data, null);
	}

	public static <T> ApiResponse<T> success(T data, PageResponse pageResponse) {
		return ApiResponse.of(200, "success", data, pageResponse);
	}

	public static <T> ApiResponse<T> fail(int status, String message) {
		return ApiResponse.of(status, message, null, null);
	}
}
