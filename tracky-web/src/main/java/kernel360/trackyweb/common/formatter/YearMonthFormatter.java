package kernel360.trackyweb.common.formatter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class YearMonthFormatter implements Formatter<YearMonth> {

	private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM");

	@Override
	public YearMonth parse(String text, Locale locale) {
		return YearMonth.parse(text, PATTERN);
	}

	@Override
	public String print(YearMonth object, Locale locale) {
		return object.format(PATTERN);
	}

}
