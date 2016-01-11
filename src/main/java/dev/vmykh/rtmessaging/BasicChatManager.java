package dev.vmykh.rtmessaging;

import java.util.HashMap;
import java.util.Map;

public final class BasicChatManager implements ChatManager {

	private final Map<String, Chat> nameToChat = new HashMap<>();

	@Override
	public boolean chatExists(String name) {
		return nameToChat.containsKey(name);
	}

	@Override
	public void createChat(String name) throws IllegalStateException {
		if (!chatExists(name)) {
			nameToChat.put(name, new Chat(name));
		} else {
			throw new IllegalStateException("Chat with name \"" + name + "\" already exists");
		}
	}

	@Override
	public Chat getChat(String name) throws IllegalArgumentException {
		return null;
	}
}
