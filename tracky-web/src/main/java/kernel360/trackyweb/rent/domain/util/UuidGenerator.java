package kernel360.trackyweb.rent.domain.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UuidGenerator {

	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";

	public static String generateUuid() {
		try {
			String randomString = getRandomString(6);
			log.info("randomString: {}", randomString);
			String uuidString = UUID.randomUUID().toString();
			log.info("uuidString: {}", uuidString);

			String input = uuidString + randomString;

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			log.info("digest: {}", digest);

			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			log.info("hash: {}", hash);


			String base62Encoded = encodeBase62(hash);
			log.info("base62Encoded: {}", base62Encoded);


			if (base62Encoded.length() < 10) {
				base62Encoded = base62Encoded + getRandomString(10);
			}

			return base62Encoded.substring(0, 10).toUpperCase();

		} catch (Exception e) {
			throw new RuntimeException("예약 번호 생성 실패", e);
		}
	}

	private static String encodeBase62(byte[] input) {
		if (input == null || input.length == 0) {
			throw new IllegalArgumentException("Input for Base62 encoding must not be null or empty");
		}

		if (BASE62.length() < 64) {
			throw new IllegalStateException("BASE62 character set must contain at least 64 characters");
		}

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
