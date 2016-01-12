package dev.vmykh.rtmessaging.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dev.vmykh.rtmessaging.ChatManager;
import dev.vmykh.rtmessaging.Events;
import dev.vmykh.rtmessaging.transport.CreateChatData;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class CreateChatListenerTest {

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();
	private ChatManager chatManager = context.mock(ChatManager.class);
	SocketIOClient client = context.mock(SocketIOClient.class);
	private CreateChatListener createChatListener = new CreateChatListener(chatManager);

	@Test
	public void itShouldCreateChatAndSendSuccessEventToClientIfThereIsNoChatWithSpecifiedName() throws Exception {
		final String chatName = "SuperChat";
		CreateChatData data = new CreateChatData(chatName);

		context.checking(new Expectations() {{
			allowing(chatManager).chatExists(chatName); will(returnValue(false));
			exactly(1).of(chatManager).createChat(chatName);
			exactly(1).of(client).sendEvent(Events.CREATE_CHAT_SUCCESS_EVENT);
		}});

		createChatListener.onData(client, data, null);
	}

	@Test
	public void itShoulSendErrorEventToClientIfThereIsAlreadyChatWithSpecifiedName() throws Exception {
		final String chatName = "AwesomeChat";
		CreateChatData data = new CreateChatData(chatName);

		context.checking(new Expectations() {{
			allowing(chatManager).chatExists(chatName); will(returnValue(true));
			exactly(1).of(client).sendEvent(Events.CREATE_CHAT_ERROR_EVENT);
		}});

		createChatListener.onData(client, data, null);
	}
}
