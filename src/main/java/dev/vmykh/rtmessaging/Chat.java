package dev.vmykh.rtmessaging;

import java.util.ArrayList;
import java.util.List;

public final class Chat {
	private final String name;
	private final List<String> participants;

	public Chat(String name, List<String> participants) {
		this.name = name;
		this.participants = participants;
	}

	public String getName() {
		return name;
	}

	public List<String> getParticipants() {
		return new ArrayList<>(participants);
	}

	public void addParticipant(String login) {
		participants.add(login);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Chat)) return false;

		Chat chat = (Chat) o;

		if (name != null ? !name.equals(chat.name) : chat.name != null) return false;
		if (participants != null ? !participants.equals(chat.participants) : chat.participants != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (participants != null ? participants.hashCode() : 0);
		return result;
	}
}
