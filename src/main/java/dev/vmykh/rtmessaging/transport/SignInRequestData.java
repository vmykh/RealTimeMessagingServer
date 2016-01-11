package dev.vmykh.rtmessaging.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class SignInRequestData {
	private final String login;

	public SignInRequestData(@JsonProperty("login") String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SignInRequestData)) return false;

		SignInRequestData that = (SignInRequestData) o;

		if (login != null ? !login.equals(that.login) : that.login != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return login != null ? login.hashCode() : 0;
	}
}
