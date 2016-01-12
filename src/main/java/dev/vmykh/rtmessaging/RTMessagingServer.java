package dev.vmykh.rtmessaging;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import dev.vmykh.rtmessaging.listener.CreateChatListener;
import dev.vmykh.rtmessaging.listener.SignInListener;
import dev.vmykh.rtmessaging.transport.CreateChatData;
import dev.vmykh.rtmessaging.transport.SignInRequestData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class RTMessagingServer {
	private final SocketIOServer socketIOServer;

	public RTMessagingServer(int port) {
		Configuration config = new Configuration();
		config.setHostname("localhost");
		config.setPort(port);
		UserManager userManager = new BasicUserManager();
		ChatManager chatManager = new BasicChatManager();
		SessionManager sessionManager = new SynchronizedSessionManager();

		socketIOServer = new SocketIOServer(config);
		socketIOServer.addEventListener(Events.SIGN_IN_REQUEST_EVENT,
				SignInRequestData.class, new SignInListener(userManager, sessionManager));
		socketIOServer.addEventListener(Events.CREATE_CHAT_EVENT,
				CreateChatData.class, new CreateChatListener(chatManager));
	}

	public void listen() throws InterruptedException {
		socketIOServer.start();
		Thread.sleep(Long.MAX_VALUE);
	}

	public void stop() {
		socketIOServer.stop();
	}
}
