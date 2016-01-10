package dev.vmykh.rtmessaging;

import io.socket.client.Manager;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class SignInTest {

	@Test
	public void signInUserWhenAppropriateRequestIsSent() throws URISyntaxException, InterruptedException {
		int port = 10000;
		RTMessagingServer server = new RTMessagingServer(port);
		RTMessagingClientStub client = new RTMessagingClientStub("http://localhost:" + port);

		startServerListeningInOtherThread(server);
		Thread.sleep(5000);
		boolean signedIn = client.signIn("Jack");

		assertTrue(signedIn);
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
}
