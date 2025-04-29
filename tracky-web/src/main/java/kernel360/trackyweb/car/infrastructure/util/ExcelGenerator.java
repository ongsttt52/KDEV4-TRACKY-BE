package kernel360.trackyweb.car.infrastructure.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.exception.ErrorCode;
import kernel360.trackycore.core.common.exception.GlobalException;
import kernel360.trackyweb.car.application.dto.request.CarsExportRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExcelGenerator {

	private static final String[] HEADERS = {
		"상태", "차량 번호", "종류", "차량 관리번호", "연식"
	};

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yy.MM.dd(E)");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	/**
	 * 운행 기록 데이터를 엑셀 파일로 생성
	 * @return 생성된 엑셀 파일의 바이트 배열
	 */
	public byte[] generateExcel(List<CarsExportRequest> request) {
		try (Workbook workbook = new XSSFWorkbook();
			 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			// 워크시트 생성
			Sheet sheet = workbook.createSheet("차량 리스트");

			// 헤더 스타일 설정
			CellStyle headerStyle = createHeaderStyle(workbook);

			// 데이터 스타일 설정
			CellStyle dataStyle = createDataStyle(workbook);

			// 헤더 행 생성
			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < HEADERS.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(HEADERS[i]);
				cell.setCellStyle(headerStyle);
				sheet.setColumnWidth(i, 4000); // 열 너비 설정
			}

			// 데이터 행 생성
			int rowNum = 1;
			for (CarsExportRequest car : request) {
				Row row = sheet.createRow(rowNum++);
				fillDriveDataRow(car, row, dataStyle);
			}

			// 워크북을 바이트 배열로 변환
			workbook.write(outputStream);
			return outputStream.toByteArray();
		} catch (IOException e) {
			log.error("엑셀 파일 생성 중 오류 발생: {}", e.getMessage(), e);
			throw GlobalException.throwError(ErrorCode.EXCEL_GENERATION_FAIL);
		}
	}

	/**
	 * 헤더 행의 스타일을 생성
	 */
	private CellStyle createHeaderStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();

		// 배경색 설정 (파란색)
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// 테두리 설정
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		// 정렬 설정
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		// 폰트 설정
		Font font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);

		return style;
	}

	/**
	 * 데이터 행의 스타일을 생성
	 */
	private CellStyle createDataStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();

		// 테두리 설정
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		// 정렬 설정
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		return style;
	}

	/**
	 * 운행 기록 데이터로 행을 채움
	 */
	private void fillDriveDataRow(CarsExportRequest car, Row row, CellStyle style) {
		// 상태
		createCell(row, 0, car.status(), style);

		// 차량 번호
		createCell(row, 1, car.carPlate(), style);

		// 종류
		createCell(row, 2, car.carType(), style);

		// 차량 관리번호
		createCell(row, 3, car.mdn(), style);

		// 연식
		createCell(row, 4, car.carYear(), style);
	}

	/**
	 * 셀을 생성하고 값과 스타일을 설정
	 */
	private void createCell(Row row, int column, String value, CellStyle style) {
		Cell cell = row.createCell(column);
		cell.setCellValue(value != null ? value : "");
		cell.setCellStyle(style);
	}

	/**
	 * LocalDateTime을 원하는 형식의 문자열로 변환
	 */
	private String formatDateTime(LocalDateTime dateTime, DateTimeFormatter formatter) {
		return dateTime != null ? dateTime.format(formatter) : "";
	}

	/**
	 * 운행 시간을 계산 (시간:분:초 형식)
	 */
	private String calculateDriveTime(LocalDateTime startTime, LocalDateTime endTime) {
		if (startTime == null || endTime == null) {
			return "0시간 0분";
		}

		long hours = java.time.Duration.between(startTime, endTime).toHours();
		long minutes = java.time.Duration.between(startTime, endTime).toMinutesPart();

		return hours + "시간 " + minutes + "분";
	}
}
