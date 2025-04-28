package kernel360.trackyweb.rent.domain.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.UUID;

public class UuidGenerator {

	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static String generateUuid() {
		try {
			String input = UUID.randomUUID().toString() + getRandomString(6);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

			String base62Encoded = encodeBase62(hash);

			return base62Encoded.substring(0, 10).toUpperCase();

		} catch (Exception e) {
			throw new RuntimeException("예약 번호 생성 실패", e);
		}
	}

	private static String encodeBase62(byte[] input) {
		StringBuilder result = new StringBuilder();
		int value = 0;
		int bitCount = 0;

		for (byte b : input) {
			value = (value << 8) | (b & 0xFF);
			bitCount += 8;

			while (bitCount >= 6) {
				int index = (value >> (bitCount - 6)) & 0x3F;
				result.append(BASE62.charAt(index));
				bitCount -= 6;
			}
		}
		return result.toString();
	}

	private static String getRandomString(int length) {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
		}
		return sb.toString();
	}
}
