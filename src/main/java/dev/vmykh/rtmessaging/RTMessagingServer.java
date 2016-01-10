package dev.vmykh.rtmessaging;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

public class RTMessagingServer {

	public static final String SIGN_IN_REQUEST_EVENT = "SIGN_IN_REQUEST";
	public static final String SIGN_IN_SUCCESS = "SIGN_IN_SUCCESS";

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
						client.sendEvent(SIGN_IN_SUCCESS);
					}
		});
	}

	public void listen() throws InterruptedException {
		socketIOServer.start();
		Thread.sleep(Long.MAX_VALUE);
	}
}
