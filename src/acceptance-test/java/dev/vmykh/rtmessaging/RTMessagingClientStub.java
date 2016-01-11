package dev.vmykh.rtmessaging;

import dev.vmykh.rtmessaging.transport.SignInRequestData;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class RTMessagingClientStub {

	public static int iter = 0;
	public static List<String> logins = new ArrayList<>();

	private final Socket socket;
	private String cookie;

	public RTMessagingClientStub(String uri) throws URISyntaxException {
		IO.Options options = new IO.Options();
		options.forceNew = true;
		this.socket = IO.socket(uri, options);
		this.cookie = null;
	}

	public String signIn(final String login) throws InterruptedException {
		logins.add(login);
		final CountDownLatch latch = new CountDownLatch(1);

		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				System.out.println("event-connect");
				SignInRequestData requestData = new SignInRequestData(login);
				JSONObject loginData = new JSONObject(requestData);
				socket.emit(Events.SIGN_IN_REQUEST_EVENT, loginData);
				System.out.println("emmitted");
			}
		}).on(Events.SIGN_IN_SUCCESS_EVENT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
//				System.out.println("inner-sign-in");
//				iter++;
				try {
					cookie = ((JSONObject)args[0]).getString("cookie");
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
				latch.countDown();
			}
		});
		socket.connect();
		latch.await(15, TimeUnit.SECONDS);
		socket.close();
		return cookie;
	}
}
