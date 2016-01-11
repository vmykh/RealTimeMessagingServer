package dev.vmykh.rtmessaging;

public interface UserManager {

	/**
	 * Creates user and generates cookies for him
	 *
	 * @param login user login
	 * @return generated {@link Cookie} for user
	 * @throws IllegalStateException if specified login is already used
	 * @throws IllegalArgumentException if specified login doesn't conforms login rules
	 */
	Cookie createUser(String login) throws IllegalStateException, IllegalArgumentException;

	/**
	 * Checks whether specified login is available
	 * @param login user login
	 * @return true if login is available, and false otherwise
	 */
	boolean isLoginAvailable(String login);
}
