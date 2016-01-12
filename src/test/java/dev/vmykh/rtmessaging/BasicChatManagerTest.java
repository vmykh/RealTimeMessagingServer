package dev.vmykh.rtmessaging;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public final class BasicChatManagerTest {

	@Test
	public void itShouldCreateChatIfThereIsNoChatWithSpecifiedName() {
		String chatName = "MegaChat";
		BasicChatManager chatManager = new BasicChatManager();

		assertFalse(chatManager.chatExists(chatName));

		chatManager.createChat(chatName);

		assertTrue(chatManager.chatExists(chatName));
	}

	@Test(expected = IllegalStateException.class)
	public void itShouldThrowExceptionIfAttemptToRecreateExistingChatIsMade() {
		String chatName = "MegaChat";
		BasicChatManager chatManager = new BasicChatManager();

		assertFalse(chatManager.chatExists(chatName));

		chatManager.createChat(chatName);

		assertTrue(chatManager.chatExists(chatName));

		// chat with specified name already exists
		chatManager.createChat(chatName);
	}

	@Test
	public void itShouldReturnsAppropriateChat() {
		String chatName = "KiloChat";
		BasicChatManager chatManager = new BasicChatManager();

		chatManager.createChat(chatName);
		Chat chat = chatManager.getChat(chatName);

		assertNotNull(chat);
		assertEquals(new Chat(chatName, new ArrayList<String>()), chat);
	}

	@Test
	public void itShouldJoinUsersIntoChat() {
		String chatName = "TeraChat";
		String laura = "Laura";
		BasicChatManager chatManager = new BasicChatManager();

		chatManager.createChat(chatName);
		Chat chat = chatManager.getChat(chatName);

		assertEquals(0, chat.getParticipants().size());

		chat.addParticipant(laura);

		chat = chatManager.getChat(chatName);
		assertThat(chat.getParticipants(), contains(laura));
	}
}
