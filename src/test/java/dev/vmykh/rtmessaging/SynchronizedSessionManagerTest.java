package dev.vmykh.rtmessaging;

import org.jmock.api.Expectation;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class SynchronizedSessionManagerTest {

	@Test
	public void itShouldHoldsSessionInfo() {
		SynchronizedSessionManager sessionManager = new SynchronizedSessionManager();
		UUID sessionId_1 = UUID.randomUUID();
		UUID sessionId_2 = UUID.randomUUID();
		String ann = "Ann";
		String emma = "Emma";

		sessionManager.addSession(sessionId_1, ann);
		sessionManager.addSession(sessionId_2, emma);

		assertEquals(ann, sessionManager.getLogin(sessionId_1));
		assertEquals(sessionId_1, sessionManager.getSessionId(ann));
		assertEquals(emma, sessionManager.getLogin(sessionId_2));
		assertEquals(sessionId_2, sessionManager.getSessionId(emma));
	}

	@Test(expected = IllegalStateException.class)
	public void itShouldThrowExceptionWhenThereIsNoSpecifiedSessionId() {
		SynchronizedSessionManager sessionManager = new SynchronizedSessionManager();
		UUID sessionId = UUID.randomUUID();
		String login = sessionManager.getLogin(sessionId);
	}

	@Test(expected = IllegalStateException.class)
	public void itShouldThrowExceptionWhenThereIsNoSpecifiedLogin() {
		SynchronizedSessionManager sessionManager = new SynchronizedSessionManager();
		String kate = "Kate";
		UUID sessionId = sessionManager.getSessionId(kate);
	}


}
