package kernel360.trackycore.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kernel360.trackycore.core.domain.entity.base.DateBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "daily_total")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTotalEntity extends DateBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "mdn", length = 11, nullable = false)
	private String mdn;

	@Column(name = "date", nullable = false)
	private String date;

	@Column(name = "daily_distance", length = 100, nullable = false)
	private String dailyDistance;
}
