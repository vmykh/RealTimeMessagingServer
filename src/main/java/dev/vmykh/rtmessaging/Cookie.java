package dev.vmykh.rtmessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Value object that encapsulates cookies. It provides proper {@link String} representation of cookies
 */
public final class Cookie {
	private static final String COOKIES_STRING_REGEX = "^id(\\d+)$";
	private static final Pattern COOKIES_PATTERN = Pattern.compile(COOKIES_STRING_REGEX);
	public final int id;

	/**
	 * Creates new cookie
	 *
	 * @param id cookie id
	 * @throws IllegalArgumentException if {@code id} is not positive
	 */
	public Cookie(int id) throws IllegalArgumentException {
		checkArgument(id > 0, "id should be positive");

		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "id" + id;
	}

	public static Cookie fromString(String cookies) {
		Matcher matcher = COOKIES_PATTERN.matcher(cookies);
		if (matcher.matches()) {
			return new Cookie(Integer.parseInt(matcher.group(1)));
		} else {
			throw new IllegalArgumentException("cookies format is wrong");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Cookie)) return false;

		Cookie cookie = (Cookie) o;

		if (id != cookie.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
