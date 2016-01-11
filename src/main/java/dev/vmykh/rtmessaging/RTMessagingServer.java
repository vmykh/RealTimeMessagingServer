package dev.vmykh.rtmessaging;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import dev.vmykh.rtmessaging.transport.SignInRequestData;
import dev.vmykh.rtmessaging.transport.SignInSuccessData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RTMessagingServer {

	public List<String> logins = new ArrayList<>();

	public static final String SIGN_IN_REQUEST_EVENT = "SIGN_IN_REQUEST";
	public static final String SIGN_IN_SUCCESS = "SIGN_IN_SUCCESS";
	public static final Random RANDOM = new Random();

	final SocketIOServer socketIOServer;

	public RTMessagingServer(int port) {
		Configuration config = new Configuration();
		config.setHostname("localhost");
		config.setPort(port);

		socketIOServer = new SocketIOServer(config);
		socketIOServer.addEventListener(SIGN_IN_REQUEST_EVENT, SignInRequestData.class,
				new DataListener<SignInRequestData>() {
					@Override
					public void onData(SocketIOClient client, SignInRequestData data, AckRequest ackSender)
							throws Exception {
						String cookie = "abcd" + Math.abs(RANDOM.nextInt(1_000_000_000));
						logins.add(data.getLogin());
						client.sendEvent(SIGN_IN_SUCCESS, new SignInSuccessData(cookie));
						System.out.println("connect for " + data.getLogin());
					}
		});
	}

	public void listen() throws InterruptedException {
		socketIOServer.start();
		Thread.sleep(Long.MAX_VALUE);
	}

	public void stop() {
		socketIOServer.stop();
	}
}
