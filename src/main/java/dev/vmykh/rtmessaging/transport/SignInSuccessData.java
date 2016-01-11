package dev.vmykh.rtmessaging.transport;

public final class SignInSuccessData {

	private final String cookie;

	public SignInSuccessData(String cookie) {
		this.cookie = cookie;
	}

	public String getCookie() {
		return cookie;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SignInSuccessData)) return false;

		SignInSuccessData that = (SignInSuccessData) o;

		if (cookie != null ? !cookie.equals(that.cookie) : that.cookie != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return cookie != null ? cookie.hashCode() : 0;
	}
}
