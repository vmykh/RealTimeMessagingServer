package dev.vmykh.rtmessaging.controlmessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInRequestMessage {
	private final String login;

	public SignInRequestMessage(@JsonProperty("login") String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
