package dev.vmykh.rtmessaging.helper;

import java.util.List;

public final class ChatInfo {
	private final String name;
	private final List<String> participants;

	public ChatInfo(String name, List<String> participants) {
		this.name = name;
		this.participants = participants;
	}

	public String getName() {
		return name;
	}

	public List<String> getParticipants() {
		return participants;
	}
}
