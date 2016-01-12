package dev.vmykh.rtmessaging.helper;

import dev.vmykh.rtmessaging.Events;
import dev.vmykh.rtmessaging.transport.SignInRequestData;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class RTMessagingClientStub {
	private final Socket socket;
	private String cookie;
	private final BlockingQueue<String> eventsMessages = new ArrayBlockingQueue<>(100);
	private final BlockingQueue<ChatInfo> chats = new ArrayBlockingQueue<>(100);

	public RTMessagingClientStub(String uri) throws URISyntaxException {
		IO.Options options = new IO.Options();
		options.forceNew = true;
		socket = IO.socket(uri, options);
		cookie = null;
		initListeners();
	}

	private void initListeners() {
		socket.on(Events.CREATE_CHAT_SUCCESS_EVENT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				try {
					eventsMessages.put(Events.CREATE_CHAT_SUCCESS_EVENT);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		socket.on(Events.CREATE_CHAT_ERROR_EVENT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				try {
					eventsMessages.put(Events.CREATE_CHAT_ERROR_EVENT);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		socket.on(Events.JOIN_CHAT_SUCCESS_EVENT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject json = (JSONObject) args[0];
				String chatName;
				JSONArray participatnsJson;
				try {
					chatName = json.getString("chatName");
					participatnsJson = json.getJSONArray("participants");
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
				List<String> participants = new ArrayList<>();
				for (int i = 0; i < participatnsJson.length(); i++) {
					try {
						participants.add(participatnsJson.getString(i));
					} catch (JSONException e) {
						throw new RuntimeException(e);
					}
				}
				try {
					eventsMessages.put(Events.JOIN_CHAT_SUCCESS_EVENT);
					chats.put(new ChatInfo(chatName, participants));
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		socket.on(Events.JOIN_CHAT_ERROR_EVENT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				try {
					eventsMessages.put(Events.JOIN_CHAT_ERROR_EVENT);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	public String signIn(final String login) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);

		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				SignInRequestData requestData = new SignInRequestData(login);
				JSONObject loginData = new JSONObject(requestData);
				socket.emit(Events.SIGN_IN_REQUEST_EVENT, loginData);
			}
		}).on(Events.SIGN_IN_SUCCESS_EVENT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				try {
					cookie = ((JSONObject) args[0]).getString("cookie");
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
				latch.countDown();
			}
		});
		socket.connect();
		latch.await(10, TimeUnit.SECONDS);
		return cookie;
	}

	public boolean createChat(String chatName) throws InterruptedException, JSONException {
		socket.emit(Events.CREATE_CHAT_EVENT, new JSONObject().put("chatName", chatName));
		String eventMessage = eventsMessages.poll(10, TimeUnit.SECONDS);
		if (eventMessage == null) {
			throw new IllegalStateException("Didn't receive any answer after createChat command");
		}
		if (eventMessage.equals(Events.CREATE_CHAT_SUCCESS_EVENT)) {
			return true;
		} else if (eventMessage.equals(Events.CREATE_CHAT_ERROR_EVENT)) {
			return false;
		} else {
			throw new IllegalStateException("Didn't receive proper answer after createChat command");
		}
	}

	public ChatInfo joinChat(String chatName) throws JSONException, InterruptedException, ChatNotFoundException {
		socket.emit(Events.JOIN_CHAT_EVENT, new JSONObject().put("chatName", chatName));
		String eventMessage = eventsMessages.poll(10, TimeUnit.SECONDS);
		if (eventMessage == null) {
			throw new IllegalStateException("Didn't receive any answer after joinChat command");
		}
		if (eventMessage.equals(Events.JOIN_CHAT_SUCCESS_EVENT)) {
			return chats.poll(10, TimeUnit.SECONDS);
		} else if (eventMessage.equals(Events.JOIN_CHAT_ERROR_EVENT)) {
			throw new ChatNotFoundException();
		} else {
			throw new IllegalStateException("Didn't receive proper answer after joinChat command");
		}
	}
}
