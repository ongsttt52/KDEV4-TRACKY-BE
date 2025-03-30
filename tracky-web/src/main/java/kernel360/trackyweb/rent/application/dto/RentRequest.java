package kernel360.trackyweb.rent.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class RentRequest {

	private String renterName;
	private String renterPhone;
	private String carPlate;
	private LocalDateTime rentStime;
	private LocalDateTime rentEtime;
	private String rentLoc;
	private String rentLat;
	private String rentLon;
	private String returnLoc;
	private String returnLat;
	private String returnLon;
	private String purpose;

}
