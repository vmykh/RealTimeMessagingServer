package dev.vmykh.rtmessaging;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CookieTest {

	@Test
	public void cookiesShouldBeEqualIfAndOnlyIfIdsAreEquals() {
		Cookie cookie_1 = new Cookie(10);
		Cookie cookie_2 = new Cookie(10);
		Cookie cookie_3 = new Cookie(12);

		assertEquals(cookie_1, cookie_2);
		assertNotEquals(cookie_1, cookie_3);
		assertNotEquals(cookie_1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cookieIdShouldBePositive() {
		Cookie cookie = new Cookie(-150);
	}

	@Test
	public void itShouldProperlyParseFromString() {
		String cookie_str_1 = "id125";
		String cookie_str_2 = "id500012";

		Cookie cookie_1 = Cookie.fromString(cookie_str_1);
		Cookie cookie_2 = Cookie.fromString(cookie_str_2);

		assertEquals(new Cookie(125), cookie_1);
		assertEquals(new Cookie(500012), cookie_2);
	}
}
