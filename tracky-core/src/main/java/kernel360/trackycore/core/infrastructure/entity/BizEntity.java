package kernel360.trackycore.core.infrastructure.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kernel360.trackycore.core.infrastructure.base.DateBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "Biz")
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BizEntity extends DateBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "biz_name")
	private String bizName;

	@Column(name = "biz_reg_num")
	private String bizRegNum;

	@Column(name = "biz_admin")
	private String bizAdmin;

	@Column(name = "biz_phone_num")
	private String bizPhoneNum;

	@Column(name = "deleted_at")
	private LocalDateTime deleteAt;

}
