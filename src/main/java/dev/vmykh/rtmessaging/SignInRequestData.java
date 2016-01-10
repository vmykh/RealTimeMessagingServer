package dev.vmykh.rtmessaging;

public class SignInRequestData {
	private final String login;

	public SignInRequestData(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
