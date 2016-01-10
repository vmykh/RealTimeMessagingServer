package dev.vmykh.rtmessaging;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class RTMessagingClientStub {

	public static final String SIGN_IN_SUCCESS_RESPONSE = "SIGN_IN_SUCCESS";
	private final Socket socket;

	public RTMessagingClientStub(String uri) throws URISyntaxException {
		this.socket = IO.socket(uri);
	}

	public boolean signIn(String login) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		socket.on(SIGN_IN_SUCCESS_RESPONSE, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				latch.countDown();
			}
		});
		socket.connect();
		return latch.await(2, TimeUnit.SECONDS);
	}
}
