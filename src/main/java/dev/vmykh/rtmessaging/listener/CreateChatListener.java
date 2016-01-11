package dev.vmykh.rtmessaging.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import dev.vmykh.rtmessaging.ChatManager;
import dev.vmykh.rtmessaging.Events;
import dev.vmykh.rtmessaging.transport.CreateChatData;

public final class CreateChatListener implements DataListener<CreateChatData> {
	private final ChatManager chatManager;
	private final SocketIOClient client;

	public CreateChatListener(ChatManager chatManager, SocketIOClient client) {
		this.chatManager = chatManager;
		this.client = client;
	}

	@Override
	public void onData(SocketIOClient client, CreateChatData data, AckRequest ackSender) throws Exception {
		String chatName = data.getChatName();
		// TODO: add thread-safety
		if (!chatManager.chatExists(chatName)) {
			chatManager.createChat(chatName);
			client.sendEvent(Events.CREATE_CHAT_SUCCESS_EVENT);
		} else {
			client.sendEvent(Events.CREATE_CHAT_ERROR_EVENT);
		}
	}
}
