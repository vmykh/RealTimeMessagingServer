package dev.vmykh.rtmessaging;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class RTMessagingClientStub {

	private final Socket socket;

	public RTMessagingClientStub(String uri) throws URISyntaxException {
		this.socket = IO.socket(uri);
	}

	public boolean signIn(final String login) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject loginJson;
				try {
					loginJson = new JSONObject().put("login", login);
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
				socket.emit(RTMessagingServer.SIGN_IN_REQUEST_EVENT, loginJson.toString());
			}
		}).on(RTMessagingServer.SIGN_IN_SUCCESS, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				latch.countDown();
			}
		});
		socket.connect();
		return latch.await(2, TimeUnit.SECONDS);
	}
}
