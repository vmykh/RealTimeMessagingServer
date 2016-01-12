package dev.vmykh.rtmessaging;

import dev.vmykh.rtmessaging.helper.ChatInfo;
import dev.vmykh.rtmessaging.helper.ChatNotFoundException;
import dev.vmykh.rtmessaging.helper.RTMessagingClientStub;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class JoinChatTest {

	public static int port;
	public RTMessagingServer server;

	@Before
	public void setUp() {
		port = generatePort();
		server = new RTMessagingServer(port);
		startServerListeningInOtherThread(server);
	}

	@Test
	public void itShouldJoinChatIfChatExists() throws URISyntaxException, InterruptedException, JSONException, ChatNotFoundException {
		RTMessagingClientStub client_1 = createClient(port);
		RTMessagingClientStub client_2 = createClient(port);
		String arthur = "Arthur";
		String larry = "Larry";
		String chatName = "Magestic Chat";

		client_1.signIn(arthur);
		client_2.signIn(larry);
		client_1.createChat(chatName);
		client_1.joinChat(chatName);
		ChatInfo chat = client_2.joinChat(chatName);

		assertThat(chat, containsParticipants(arthur, larry));
	}

	@Test(expected = ChatNotFoundException.class)
	public void itShouldNotJoinChatIfChatDoesntExist() throws URISyntaxException, InterruptedException, ChatNotFoundException, JSONException {
		RTMessagingClientStub client = createClient(port);

		client.signIn("Mitchel");
		client.joinChat("Non-existing chat");
	}

	@After
	public void tearDown() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// helpers
	public static void startServerListeningInOtherThread(final RTMessagingServer server) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					server.listen();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}

	public static RTMessagingClientStub createClient(int port) throws URISyntaxException {
		return new RTMessagingClientStub("http://localhost:" + port);
	}

	public int generatePort() {
		Random random = new Random();
		// range: [10_000, 25_000)
		return 10000 + Math.abs(random.nextInt()) % 15000;
	}

	// custom matchers
	public static Matcher<ChatInfo> containsParticipants(final String ... logins) {
		checkArgument(logins.length > 0);

		return new BaseMatcher<ChatInfo>() {
			@Override
			public boolean matches(Object item) {
				if (item == null) {
					return false;
				}
				ChatInfo chat = (ChatInfo) item;
				Set<String> participants = new HashSet<>();
				for (String participant : chat.getParticipants()) {
					participants.add(participant);
				}
				for (String login : logins) {
					if (!participants.contains(login)) {
						return false;
					}
				}
				return true;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("contains participants: " + Arrays.toString(logins));
			}
		};
	}
}
