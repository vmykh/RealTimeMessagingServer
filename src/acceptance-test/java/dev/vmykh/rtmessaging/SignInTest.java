package dev.vmykh.rtmessaging;

import dev.vmykh.rtmessaging.helper.RTMessagingClientStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.Random;

import static org.junit.Assert.*;

public class SignInTest {

	public static int port;
	public RTMessagingServer server;

	@Before
	public void setUp() {
		port = generatePort();
		server = new RTMessagingServer(port);
		startServerListeningInOtherThread(server);
	}

	@Test
	public void itShouldReturnCookiesWhenLoginIsAvailable() throws URISyntaxException, InterruptedException {
		RTMessagingClientStub client = createClient(port);

		String cookie = client.signIn("Jack");

		assertNotNull(cookie);
		assertTrue(cookie.length() > 0);
	}

	@Test
	public void cookiesShouldBeDifferentForDifferentClients() throws URISyntaxException, InterruptedException {
		RTMessagingClientStub client_1 = createClient(port);
		RTMessagingClientStub client_2 = createClient(port);
		RTMessagingClientStub client_3 = createClient(port);

//		Logger logger = Logger.getLogger(Manager.class.getName());
//		logger.setLevel(Level.ALL);
//		Handler consoleHandler = new ConsoleHandler();
//		consoleHandler.setLevel(Level.FINE);
//		logger.addHandler(consoleHandler);

		String cookie_1 = client_1.signIn("Lukas");
		String cookie_2 = client_2.signIn("Simon");
		String cookie_3 = client_3.signIn("Barry");

		assertNotNull(cookie_1);
		assertNotNull(cookie_2);
		assertNotNull(cookie_3);
		assertNotEquals(cookie_1, cookie_2);
		assertNotEquals(cookie_1, cookie_3);
		assertNotEquals(cookie_2, cookie_3);
	}

	@Test
	public void itShouldReturnErrorIfLoginIsAlreadyUsed() throws InterruptedException, URISyntaxException {
		RTMessagingClientStub client_1 = createClient(port);
		RTMessagingClientStub client_2 = createClient(port);
		RTMessagingClientStub client_3 = createClient(port);

		String cookie_1 = client_1.signIn("Lukas");
		String cookie_2 = client_2.signIn("Simon");
		String cookie_3 = client_3.signIn("Lukas");

		assertNotNull(cookie_1);
		assertNotNull(cookie_2);
		assertNull(cookie_3);
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
