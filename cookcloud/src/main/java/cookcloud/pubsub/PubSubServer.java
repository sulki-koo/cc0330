package cookcloud.pubsub;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/pubsub")
public class PubSubServer {
	// 세션 ID를 키로, Session을 값으로 하는 Map
	private static final Map<String, Session> sessionMap = Collections.synchronizedMap(new HashMap<>());

	@OnOpen
	public void onOpen(Session session) {
		sessionMap.put(session.getId(), session);
		System.out.println("New subscriber connected: " + session.getId());
		System.out.println("Total connected sessions: " + sessionMap.size());
	}

	@OnMessage
	public void onMessage(String message, Session sender) {
		// 예제에서는 클라이언트가 보낸 메시지에 따라, 그 클라이언트(세션)에게 에코 메시지를 전송합니다.
		System.out.println("Received message from session " + sender.getId() + ": " + message);
		sendMessageToSession(sender.getId(), "Echo: " + message);
	}

	@OnClose
	public void onClose(Session session) {
		sessionMap.remove(session.getId());
		System.out.println("Subscriber disconnected: " + session.getId());
		System.out.println("Total connected sessions: " + sessionMap.size());
	}

	// 특정 세션 ID에 메시지 전송
	public static void sendMessageToSession(String sessionId, String message) {
		Session session = sessionMap.get(sessionId);
		if (session != null) {
			try {
				session.getBasicRemote().sendText(message);
				System.out.println("Message sent to session " + sessionId + ": " + message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No session found with ID: " + sessionId);
		}
	}
}