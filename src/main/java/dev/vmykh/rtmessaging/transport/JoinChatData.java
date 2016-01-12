package dev.vmykh.rtmessaging.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class JoinChatData {
	private final String chatName;

	public JoinChatData(@JsonProperty("chatName") String chatName) {
		this.chatName = chatName;
	}

	public String getChatName() {
		return chatName;
	}
}
