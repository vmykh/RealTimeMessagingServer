package dev.vmykh.rtmessaging.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dev.vmykh.rtmessaging.Cookie;
import dev.vmykh.rtmessaging.ErrorMessages;
import dev.vmykh.rtmessaging.Events;
import dev.vmykh.rtmessaging.UserManager;
import dev.vmykh.rtmessaging.transport.ErrorData;
import dev.vmykh.rtmessaging.transport.SignInRequestData;
import dev.vmykh.rtmessaging.transport.SignInSuccessData;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class SignInListenerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	UserManager userManager = context.mock(UserManager.class);
	SocketIOClient client = context.mock(SocketIOClient.class);
	SignInListener signInListener = new SignInListener(userManager);

	@Test
	public void itShouldSendCookiesToClientWhenLoginIsAvailable() throws Exception {
		final String harry = "Harry";
		final Cookie cookie = new Cookie(1234);
		SignInRequestData request = new SignInRequestData(harry);

		context.checking(new Expectations() {{
			allowing(userManager).isLoginAvailable(harry); will(returnValue(true));
			allowing(userManager).createUser(harry); will(returnValue(cookie));
			exactly(1).of(client).sendEvent(Events.SIGN_IN_SUCCESS_EVENT, new SignInSuccessData(cookie.toString()));
		}});

		signInListener.onData(client, request, null);
	}

	@Test
	public void itShouldSendErrorToClientWhenLoginIsAlreadyUsed() throws Exception {
		final String thomas = "Thomas";
		SignInRequestData request = new SignInRequestData(thomas);

		context.checking(new Expectations() {{
			allowing(userManager).isLoginAvailable(thomas); will(returnValue(false));
			exactly(1).of(client).sendEvent(
					Events.SIGN_IN_ERROR_EVENT, new ErrorData(ErrorMessages.LOGIN_IS_ALREADY_USED));
		}});

		signInListener.onData(client, request, null);
	}
}
