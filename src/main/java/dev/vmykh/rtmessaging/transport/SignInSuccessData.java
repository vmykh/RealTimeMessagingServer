package dev.vmykh.rtmessaging.transport;

public class SignInSuccessData {

	private final String cookie;

	public SignInSuccessData(String cookie) {
		this.cookie = cookie;
	}

	public String getCookie() {
		return cookie;
	}
}
