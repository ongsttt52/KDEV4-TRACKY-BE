package kernel360.trackyemulator.presentation.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CycleLogResponse {
	private String mdn;
	private double lat;
	private double lon;
	private int spd;
}