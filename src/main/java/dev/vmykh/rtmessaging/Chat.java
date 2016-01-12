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
}
