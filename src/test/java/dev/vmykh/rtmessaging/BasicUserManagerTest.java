package dev.vmykh.rtmessaging;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicUserManagerTest {

	private UserManager userManager = new BasicUserManager();

	@Test
	public void itShouldReturnProperCookieIfLoginIsAvailable() {
		Cookie jack_cookie = userManager.createUser("Jack");
		Cookie charlie_cookie = userManager.createUser("Charlie");
		Cookie dustin_cookie = userManager.createUser("Dustin");

		assertNotNull(jack_cookie);
		assertNotNull(charlie_cookie);
		assertNotNull(dustin_cookie);
		assertNotEquals(jack_cookie, charlie_cookie);
		assertNotEquals(dustin_cookie, jack_cookie);
		assertNotEquals(charlie_cookie, dustin_cookie);
	}

	@Test
	public void itShouldProperlyCheckWhetherLoginIsAvailable() {
		String jack = "Jack";
		String charlie = "Charlie";
		
		assertTrue(userManager.isLoginAvailable(jack));
		assertTrue(userManager.isLoginAvailable(charlie));
		
		userManager.createUser("Jack");
		
		assertFalse(userManager.isLoginAvailable(jack));
		assertTrue(userManager.isLoginAvailable(charlie));
		
		userManager.createUser("Charlie");

		assertFalse(userManager.isLoginAvailable(jack));
		assertFalse(userManager.isLoginAvailable(charlie));
	}

	@Test(expected = IllegalStateException.class)
	public void itShouldThrowExceptionAfterAttemptToCreateUserWithAlreadyUsedLogin() {
		String robert = "Robert";

		Cookie cookie = userManager.createUser(robert);

		assertNotNull(cookie);

		// login is already used
		userManager.createUser(robert);
	}

	@Test
	public void itShouldThrowExceptionIfLoginIsIncorrect() {
		int exceptions = 0;

		try {
			userManager.createUser(null);
		} catch (IllegalArgumentException e) {
			exceptions++;
		}

		try {
			userManager.createUser("");
		} catch (IllegalArgumentException e) {
			exceptions++;
		}

		try {
			userManager.isLoginAvailable(null);
		} catch (IllegalArgumentException e) {
			exceptions++;
		}

		try {
			userManager.isLoginAvailable("");
		} catch (IllegalArgumentException e) {
			exceptions++;
		}

		// all four calls should return exception
		assertEquals(4, exceptions);
	}
}
