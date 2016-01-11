package dev.vmykh.rtmessaging;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Random;

import static org.junit.Assert.*;

public class CreateChatListener {

	public static int port;
	public RTMessagingServer server;

	@Before
	public void setUp() {
		port = generatePort();
		server = new RTMessagingServer(port);
		startServerListeningInOtherThread(server);
	}

	@Test
	public void itShouldCreateChatIfThereIsNoChatWithSameName()
			throws URISyntaxException, InterruptedException, JSONException {
		RTMessagingClientStub client = createClient(port);

		String cookie = client.signIn("Albert");
		boolean chatCreated = client.createChat("chat-1");

		assertTrue(chatCreated);
	}

	@Test
	public void itShouldNotCreateChatIfThereIsAlreadyChatWithSameName()
			throws URISyntaxException, InterruptedException, JSONException {
		RTMessagingClientStub client = createClient(port);

		String cookie = client.signIn("Morris");
		boolean chatCreated = client.createChat("chat-1");
		boolean sameChatCreated = client.createChat("chat-1");

		assertTrue(chatCreated);
		assertFalse(sameChatCreated);
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
}
