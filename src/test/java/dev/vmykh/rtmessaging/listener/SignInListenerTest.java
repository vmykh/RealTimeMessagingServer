package dev.vmykh.rtmessaging.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dev.vmykh.rtmessaging.*;
import dev.vmykh.rtmessaging.transport.ErrorData;
import dev.vmykh.rtmessaging.transport.SignInRequestData;
import dev.vmykh.rtmessaging.transport.SignInSuccessData;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

public class SignInListenerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	UserManager userManager = context.mock(UserManager.class);
	SocketIOClient client = context.mock(SocketIOClient.class);
	SessionManager sessionManager = context.mock(SessionManager.class);
	SignInListener signInListener = new SignInListener(userManager, sessionManager);
	UUID sessionId = UUID.randomUUID();

	@Test
	public void itShouldSendCookiesToClientWhenLoginIsAvailable() throws Exception {
		final String harry = "Harry";
		final Cookie cookie = new Cookie(1234);
		SignInRequestData request = new SignInRequestData(harry);

		context.checking(new Expectations() {{
			ignoring(sessionManager);
			allowing(client).getSessionId(); will(returnValue(sessionId));
			allowing(userManager).isLoginAvailable(harry); will(returnValue(true));
			allowing(userManager).createUser(harry); will(returnValue(cookie));
			exactly(1).of(client).sendEvent(Events.SIGN_IN_SUCCESS_EVENT, new SignInSuccessData(cookie.toString()));
		}});

		signInListener.onData(client, request, null);
	}

	@Test
	public void itShouldAddNewSessionIfLoginIsAvailable() throws Exception {
		final String eva = "Eva";
		final Cookie cookie = new Cookie(1234);
		SignInRequestData request = new SignInRequestData(eva);

		context.checking(new Expectations() {{
			allowing(client).sendEvent(with(any(String.class)), with(any(Object[].class)));
			allowing(client).getSessionId(); will(returnValue(sessionId));
			allowing(userManager).isLoginAvailable(eva); will(returnValue(true));
			allowing(userManager).createUser(eva); will(returnValue(cookie));
			exactly(1).of(sessionManager).addSession(sessionId, eva);
		}});

		signInListener.onData(client, request, null);
	}

	@Test
	public void itShouldSendErrorToClientWhenLoginIsAlreadyUsed() throws Exception {
		final String thomas = "Thomas";
		final UUID sessionId = UUID.randomUUID();
		SignInRequestData request = new SignInRequestData(thomas);

		context.checking(new Expectations() {{
			ignoring(sessionManager);
			allowing(client).getSessionId(); will(returnValue(sessionId));
			allowing(userManager).isLoginAvailable(thomas); will(returnValue(false));
			exactly(1).of(client).sendEvent(
					Events.SIGN_IN_ERROR_EVENT, new ErrorData(ErrorMessages.LOGIN_IS_ALREADY_USED));
		}});

		signInListener.onData(client, request, null);
	}
}
