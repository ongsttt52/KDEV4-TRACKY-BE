package kernel360.trackyweb.emitter;

import kernel360.trackyweb.realtime.application.dto.request.CycleGpsRequest;

public record GpsPointResponse(
	double lat,
	double lon,
	int ang,
	int spd,
	String oTime,
	double sum
) {
	public static GpsPointResponse from(CycleGpsRequest req) {
		return new GpsPointResponse(
			req.gpsInfo().getLat(),
			req.gpsInfo().getLon(),
			req.gpsInfo().getAng(),
			req.gpsInfo().getSpd(),
			req.oTime().toString(),
			req.gpsInfo().getSum()
		);
	}
}
