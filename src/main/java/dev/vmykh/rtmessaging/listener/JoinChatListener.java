package dev.vmykh.rtmessaging.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import dev.vmykh.rtmessaging.Chat;
import dev.vmykh.rtmessaging.ChatManager;
import dev.vmykh.rtmessaging.Events;
import dev.vmykh.rtmessaging.SessionManager;
import dev.vmykh.rtmessaging.transport.ChatData;
import dev.vmykh.rtmessaging.transport.JoinChatData;

public final class JoinChatListener implements DataListener<JoinChatData> {
	private final SessionManager sessionManager;
	private final ChatManager chatManager;

	public JoinChatListener(SessionManager sessionManager, ChatManager chatManager) {
		this.sessionManager = sessionManager;
		this.chatManager = chatManager;
	}

	@Override
	public void onData(SocketIOClient client, JoinChatData data, AckRequest ackSender) throws Exception {
		String chatName = data.getChatName();
		if (chatManager.chatExists(chatName)) {
			String userLogin = sessionManager.getLogin(client.getSessionId());
			chatManager.joinChat(userLogin, chatName);
			Chat chat = chatManager.getChat(chatName);
			ChatData chatData = new ChatData(chat.getName(), chat.getParticipants());
			client.sendEvent(Events.JOIN_CHAT_SUCCESS_EVENT, chatData);
		} else {
			client.sendEvent(Events.JOIN_CHAT_ERROR_EVENT);
		}
	}
}
