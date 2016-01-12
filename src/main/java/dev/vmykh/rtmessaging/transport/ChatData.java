package dev.vmykh.rtmessaging.transport;

import java.util.List;

public class ChatData {
	private final String chatName;
	private final List<String> participants;

	public ChatData(String chatName, List<String> participants) {
		this.chatName = chatName;
		this.participants = participants;
	}

	public String getChatName() {
		return chatName;
	}

	public List<String> getParticipants() {
		return participants;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ChatData)) return false;

		ChatData chatData = (ChatData) o;

		if (chatName != null ? !chatName.equals(chatData.chatName) : chatData.chatName != null) return false;
		if (participants != null ? !participants.equals(chatData.participants) : chatData.participants != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = chatName != null ? chatName.hashCode() : 0;
		result = 31 * result + (participants != null ? participants.hashCode() : 0);
		return result;
	}
}
