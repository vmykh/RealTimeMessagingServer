package dev.vmykh.rtmessaging;

/**
 * Class contains event name constants
 */
public final class Events {
	private Events() {}

	public static final String SIGN_IN_REQUEST_EVENT = "SIGN_IN_REQUEST";
	public static final String SIGN_IN_SUCCESS_EVENT = "SIGN_IN_SUCCESS";
	public static final String SIGN_IN_ERROR_EVENT = "SIGN_IN_ERROR";
	public static final String CREATE_CHAT_EVENT = "CREATE_CHAT_EVENT";
	public static final String CHAT_CREATE_SUCCESS_EVENT = "CHAT_CREATE_SUCCESS_EVENT";
	public static final String CREATE_CHAT_ERROR_EVENT = "CHAT_CREATE_ERROR_EVENT";
}
