package kernel360.trackycore.core.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarEventEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "mdn", length = 11, nullable = false)
	private String mdn;

	@Column(name = "type", length = 20, nullable = false)
	private String type;

	@Column(name = "event_at", nullable = false)
	private LocalDateTime eventAt;
}
