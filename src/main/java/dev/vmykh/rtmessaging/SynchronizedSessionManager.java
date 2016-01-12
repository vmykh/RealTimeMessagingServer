package dev.vmykh.rtmessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SynchronizedSessionManager implements SessionManager {
	private final Map<UUID, String> sessionIdToLogin = new HashMap<>();
	private final Map<String, UUID> loginToSessionId = new HashMap<>();

	@Override
	public synchronized void addSession(UUID sessionId, String login) throws IllegalStateException {
		if (sessionIdToLogin.containsKey(sessionId)) {
			throw new IllegalStateException("There is already such session id: " + sessionId.toString());
		}
		if (loginToSessionId.containsKey(login)) {
			throw new IllegalStateException("There is already such login: " + login);
		}
		sessionIdToLogin.put(sessionId, login);
		loginToSessionId.put(login, sessionId);
	}

	@Override
	public synchronized UUID getSessionId(String login) throws IllegalStateException {
		if (loginToSessionId.containsKey(login)) {
			return loginToSessionId.get(login);
		} else {
			throw new IllegalStateException("There is no session for such login: " + login);
		}
	}

	@Override
	public synchronized String getLogin(UUID sessionId) throws IllegalStateException {
		if (sessionIdToLogin.containsKey(sessionId)) {
			return sessionIdToLogin.get(sessionId);
		} else {
			throw new IllegalStateException("There is no session for such id: " + sessionId.toString());
		}
	}
}
