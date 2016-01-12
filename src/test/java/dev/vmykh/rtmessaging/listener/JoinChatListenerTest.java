package dev.vmykh.rtmessaging.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dev.vmykh.rtmessaging.*;
import dev.vmykh.rtmessaging.transport.ChatData;
import dev.vmykh.rtmessaging.transport.JoinChatData;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static java.util.Arrays.asList;

public class JoinChatListenerTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();
	SocketIOClient client = context.mock(SocketIOClient.class);
	SessionManager sessionManager = context.mock(SessionManager.class);
	ChatManager chatManager = context.mock(ChatManager.class);
	JoinChatListener joinChatListener = new JoinChatListener(sessionManager, chatManager);
	UUID sessionId = UUID.randomUUID();

	@Test
	public void itShouldJoinUserToChat() throws Exception {
		final String olivia = "Olivia";
		final String chatName = "BetaChat";
		final Chat chat = new Chat(chatName, asList("Kira", "Olivia"));
		final ChatData chatData = new ChatData(chatName, asList("Kira", "Olivia"));
		final JoinChatData joinChatData = new JoinChatData(chatName);

		context.checking(new Expectations() {{
			allowing(client).getSessionId(); will(returnValue(sessionId));
			allowing(sessionManager).getLogin(sessionId); will(returnValue(olivia));
			allowing(chatManager).chatExists(chatName); will(returnValue(true));
			allowing(chatManager).getChat(chatName); will(returnValue(chat));
			exactly(1).of(chatManager).joinChat(olivia, chatName);
			exactly(1).of(client).sendEvent(Events.JOIN_CHAT_SUCCESS_EVENT, chatData);
		}});

		joinChatListener.onData(client, joinChatData, null);
	}

	@Test
	public void itShouldSendErrorWhenThereIsNoChatWithSpecifiedName() throws Exception {
		final String nancy = "Nancy";
		final String chatName = "GammaChat";
		final JoinChatData joinChatData = new JoinChatData(chatName);

		context.checking(new Expectations() {{
			allowing(client).getSessionId(); will(returnValue(sessionId));
			allowing(sessionManager).getLogin(sessionId); will(returnValue(nancy));
			allowing(chatManager).chatExists(chatName); will(returnValue(false));
			exactly(1).of(client).sendEvent(Events.JOIN_CHAT_ERROR_EVENT);
		}});

		joinChatListener.onData(client, joinChatData, null);
	}
}
