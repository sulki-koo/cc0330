package cookcloud.pubsub;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/pubsub")
public class PubSubServer {
	private static final Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

	@OnOpen
	public void onOpen(Session session) {
		clients.add(session);
		System.out.println("New subscriber connected: " + session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session sender) {
		// 클라이언트가 보내는 메시지에서 memId와 메시지 내용 분리
		String[] parts = message.split(":", 2);

		if (parts.length == 2) {
			String memId = parts[0]; // memId는 메시지의 앞부분
			String content = parts[1]; // 메시지 본문

			// 현재 세션에 memId 저장 (세션 객체에 UserProperties를 사용하여 memId 저장)
			sender.getUserProperties().put("memId", memId);
			System.out.println("User with memId " + memId + " connected and received message: " + content);

			// 특정 memId에만 메시지 전송
			sendMessageToMemId(memId, "Message for " + memId + ": " + content);
		} else {
			System.out.println("Invalid message format");
		}

	}

	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
		System.out.println("Subscriber disconnected: " + session.getId());
	}

	public static void broadcastMessage(String message) {
		synchronized (clients) {
			for (Session client : clients) {
				try {
					client.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 특정 memId에 메시지 전송
	public static void sendMessageToMemId(String memId, String message) {
		synchronized (clients) {
			// 모든 세션을 탐색하여 특정 memId를 가진 세션을 찾음
			for (Session session : clients) {
				String sessionMemId = (String) session.getUserProperties().get("memId");

				if (memId.equals(sessionMemId)) {
					try {
						session.getBasicRemote().sendText(message); // 해당 세션에만 메시지 전송
						System.out.println("Message sent to memId " + memId);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
