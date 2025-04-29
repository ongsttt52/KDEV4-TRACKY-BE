package kernel360.trackyweb.drive.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import kernel360.trackycore.core.domain.entity.DriveEntity;

public record DrivesExportRequest(
	Long id,                // 운행 ID
	LocalDateTime driveOnTime,    // 일자
	String purpose,         // 목적
	String carPlate,        // 차량번호
	String renterName,      // 사용자
	double driveDistance,   // 운행거리(km)
	LocalDateTime driveOffTime    // 도착 시간
) {
	public static List<DrivesExportRequest> from(List<DriveEntity> drives) {
		return drives.stream()
			.map(drive -> new DrivesExportRequest(
				drive.getId(),
				drive.getDriveOnTime(),
				drive.getRent() != null ? drive.getRent().getPurpose() : "",
				formatCarInfo(drive),
				drive.getRent() != null ? drive.getRent().getRenterName() : "",
				drive.getDriveDistance() / 1000.0,    // 미터를 킬로미터로 변환
				drive.getDriveOffTime()
			))
			.toList();
	}

	private static String formatCarInfo(DriveEntity drive) {
		if (drive.getCar() == null) {
			return "";
		}

		String plate = drive.getCar().getCarPlate();
		String mdn = drive.getCar().getMdn();

		if (mdn == null || mdn.isEmpty()) {
			return plate;
		}

		return plate + "\n    " + mdn; // 4칸 들여쓰기로 MDN 표시
	}
}
