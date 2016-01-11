package dev.vmykh.rtmessaging.controlmessage;

public class SignInSuccessMessage {

	private final String cookie;

	public SignInSuccessMessage(String cookie) {
		this.cookie = cookie;
	}

	public String getCookie() {
		return cookie;
	}
}
