package dev.vmykh.rtmessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public final class BasicUserManager implements UserManager {

	private static final Random RANDOM = new Random();
	private static final int MIN_COOKIE_ID = 1_000_000;
	private static final int MAX_COOKIE_ID = 9_999_999;
	private final Map<String, Cookie> loginToCookie = new HashMap<>();
	private final Map<Cookie, String> cookieToLogin = new HashMap<>();


	/**
	 * Creates user and generates cookies for him
	 *
	 * @param login user login
	 * @return generated {@link Cookie} for user
	 * @throws IllegalStateException if specified login is already used
	 * @throws IllegalArgumentException if specified login is null or zero-length
	 */
	@Override
	public Cookie createUser(String login) throws IllegalStateException {
		checkArgument(isLoginValid(login), "login is incorrect");
		checkState(isLoginAvailable(login), "login is already used");

		int cookieId = generateCookieId();
		Cookie cookie = new Cookie(cookieId);
		loginToCookie.put(login, cookie);
		cookieToLogin.put(cookie, login);
		return cookie;
	}

	/**
	 * Checks whether specified login is available
	 * @param login user login
	 * @return true if login is available, and false otherwise
	 */
	@Override
	public boolean isLoginAvailable(String login) {
		checkArgument(isLoginValid(login));

		return !loginToCookie.containsKey(login);
	}

	private static int generateCookieId() {
		return MIN_COOKIE_ID + Math.abs(RANDOM.nextInt(MAX_COOKIE_ID - MIN_COOKIE_ID + 1));
	}

	private static boolean isLoginValid(String login) {
		return login != null && login.length() > 0;
	}
}
