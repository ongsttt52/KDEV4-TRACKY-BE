package kernel360.trackyweb.reservation.presentation.dto;

public record ReservationWithCarResponse(
	ReservationResponse reservation,
	CarResponse car
) {
	public static ReservationWithCarResponse of(
		ReservationResponse reservation,
		CarResponse car
	) {
		return new ReservationWithCarResponse(reservation, car);
	}
}
