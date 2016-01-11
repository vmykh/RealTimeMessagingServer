package dev.vmykh.rtmessaging.transport;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CreateChatData {
	private final String chatName;

	public CreateChatData(@JsonProperty("chatName") String chatName) {
		this.chatName = chatName;
	}

	public String getChatName() {
		return chatName;
	}
}
