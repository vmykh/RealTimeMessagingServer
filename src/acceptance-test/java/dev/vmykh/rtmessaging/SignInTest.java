package dev.vmykh.rtmessaging;

import org.junit.Test;

import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class SignInTest {

	@Test
	public void signInUserWhenAppropriateRequestIsSent() throws URISyntaxException, InterruptedException {
		int port = 10000;
		RTMessagingServer server = new RTMessagingServer(port);
		RTMessagingClientStub client = new RTMessagingClientStub("localhost:" + port);

		startServerListeningInOtherThread(server);
		boolean signedIn = client.signIn("Jack");

		assertTrue(signedIn);
	}

	// helpers
	public static void startServerListeningInOtherThread(final RTMessagingServer server) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				server.listen();
			}
		}).start();
	}
}
