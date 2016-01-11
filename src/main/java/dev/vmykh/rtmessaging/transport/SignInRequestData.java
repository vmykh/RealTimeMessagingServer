package dev.vmykh.rtmessaging.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInRequestData {
	private final String login;

	public SignInRequestData(@JsonProperty("login") String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
