package dev.vmykh.rtmessaging;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

}
