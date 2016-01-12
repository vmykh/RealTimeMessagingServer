package dev.vmykh.rtmessaging;

import java.util.UUID;

public interface SessionManager {

	void addSession(UUID sessionId, String login) throws IllegalStateException;

	UUID getSessionId(String login) throws IllegalStateException;

	String getLogin(UUID sessionId) throws IllegalStateException;
}
