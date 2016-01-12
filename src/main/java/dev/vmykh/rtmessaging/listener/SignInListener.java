package dev.vmykh.rtmessaging.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import dev.vmykh.rtmessaging.*;
import dev.vmykh.rtmessaging.transport.ErrorData;
import dev.vmykh.rtmessaging.transport.SignInRequestData;
import dev.vmykh.rtmessaging.transport.SignInSuccessData;

public final class SignInListener implements DataListener<SignInRequestData> {

	private final UserManager userManager;
	private final SessionManager sessionManager;

	public SignInListener(UserManager userManager, SessionManager sessionManager) {
		this.userManager = userManager;
		this.sessionManager = sessionManager;
	}

	@Override
	public void onData(SocketIOClient client, SignInRequestData data, AckRequest ackSender) throws Exception {
		String login = data.getLogin();
		// TODO: add thread-safety
		if (userManager.isLoginAvailable(login)) {
			Cookie cookie = userManager.createUser(login);
			sessionManager.addSession(client.getSessionId(), login);
			client.sendEvent(Events.SIGN_IN_SUCCESS_EVENT, new SignInSuccessData(cookie.toString()));
		} else {
			client.sendEvent(Events.SIGN_IN_ERROR_EVENT, new ErrorData(ErrorMessages.LOGIN_IS_ALREADY_USED));
		}
	}
}
