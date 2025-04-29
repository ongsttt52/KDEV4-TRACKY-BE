package kernel360.trackyweb.rent.application.dto.request;

import java.time.LocalDateTime;

public record RentCreateRequest(
	String mdn,
	String renterName,
	String renterPhone,
	LocalDateTime rentStime,
	LocalDateTime rentEtime,
	String rentLoc,
	int rentLat,
	int rentLon,
	String returnLoc,
	int returnLat,
	int returnLon,
	String purpose
) {

}
