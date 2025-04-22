package kernel360.trackycore.core.domain.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// 복합 키 클래스
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GpsHistoryId implements Serializable {
	private long drive;
	private long driveSeq;
}
