package kernel360.trackyweb.Biz.infrastructure.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "Biz")
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BizEntity {

	@Id
	private Long id;

	@Column(name = "biz_name")
	private String bizName;

	@Column(name = "biz_reg_num")
	private String bizRegNum;

	@Column(name = "biz_admin")
	private String bizAdmin;

	@Column(name = "biz_phone_num")
	private String bizPhoneNum;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deleteAt;

}
