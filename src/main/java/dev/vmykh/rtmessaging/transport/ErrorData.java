package dev.vmykh.rtmessaging.transport;

public final class ErrorData {
	private final String errorMessage;

	public ErrorData(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ErrorData)) return false;

		ErrorData errorData = (ErrorData) o;

		if (errorMessage != null ? !errorMessage.equals(errorData.errorMessage) : errorData.errorMessage != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return errorMessage != null ? errorMessage.hashCode() : 0;
	}
}
