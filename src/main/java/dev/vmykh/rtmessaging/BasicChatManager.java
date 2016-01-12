package dev.vmykh.rtmessaging;

import java.util.ArrayList;
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
			nameToChat.put(name, new Chat(name, new ArrayList<String>()));
		} else {
			throw new IllegalStateException("Chat with name \"" + name + "\" already exists");
		}
	}

	@Override
	public Chat getChat(String name) throws IllegalStateException {
		if (nameToChat.containsKey(name)) {
			return nameToChat.get(name);
		} else {
			throw new IllegalStateException("There is no chat with name: " + name);
		}
	}

	@Override
	public void joinChat(String login, String chatName) throws IllegalStateException {
		if (nameToChat.containsKey(chatName)) {
			nameToChat.get(chatName).addParticipant(login);
		} else {
			throw new IllegalStateException("There is no chat with name: " + chatName);
		}
	}
}
