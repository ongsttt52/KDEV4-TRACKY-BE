package kernel360.trackyweb.emitter;

import kernel360.trackyweb.realtime.application.dto.request.CycleGpsRequest;

public record GpsPointResponse(
	double lat,
	double lon,
	int ang,
	int spd,
	String oTime
) {
	public static GpsPointResponse from(CycleGpsRequest req) {
		return new GpsPointResponse(
			req.gpsInfo().getLat() / 1_000_000.0,
			req.gpsInfo().getLon() / 1_000_000.0,
			req.gpsInfo().getAng(),
			req.gpsInfo().getSpd(),
			req.oTime().toString()
		);
	}
}
