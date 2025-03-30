package cookcloud.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cookcloud.entity.Follows;
import cookcloud.entity.Message;
import cookcloud.entity.Recipe;
import cookcloud.pubsub.PubSubServer;
import cookcloud.repository.FollowsRepository;
import cookcloud.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private FollowsRepository followsRepository;

	// 메시지 목록 조회
	public List<Message> getMessages(String memId) {
		return messageRepository.findByMemId(memId);
	}

	// 메시지 읽음 처리
	public void markMessageAsRead(Long messageId) {
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new RuntimeException("Message not found"));
		message.setMessageIsRead("Y");
		messageRepository.save(message);
	}

	// 메시지 삭제
	public void deleteMessage(Long messageId) {
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new RuntimeException("Message not found"));
		message.setMessageIsDeleted("Y");
		messageRepository.save(message);
	}

	@Transactional
	public void sendMessage(Message message) {
		message.setMessageSendAt(LocalDateTime.now());
		message.setMessageIsRead("N");
		message.setMessageIsDeleted("N");
		messageRepository.save(message);
	}

	// 공통 메시지 전송 메소드
	public void likeMessage(String sendToMemid, String nickname, String type) {
		String message = "";
		if ("recipe".equals(type)) {
			message = String.format("%s님이 회원님의 레시피를 좋아합니다.", nickname);
		} else if ("review".equals(type)) {
			message = String.format("%s님이 회원님의 리뷰를 좋아합니다.", nickname);
		}
		PubSubServer.sendMessageToSession(sendToMemid, message);
	}
	
	public void followMessage(String sendToMemid, String nickname) {
		String message = String.format("%s님이 회원님을 팔로우했습니다.", nickname);
        PubSubServer.sendMessageToSession(sendToMemid, message);
	}
	
	
	// 레시피 작성 시, 저장과 동시에 구독자에게 새 레시피 알림 전송
    public void notifyFollowersNewRecipe(Recipe recipe) {
        // 팔로워 목록 조회 (팔로우 테이블에서 recipe.getMemId()를 팔로우한 모든 사용자를 조회)
        List<Follows> followers = followsRepository.findAll()
                .stream()
                .filter(f -> f.getFollowerId().equals(recipe.getMemId()))
                .collect(Collectors.toList());
        for (Follows follow : followers) {
            String message = String.format("%s님이 새 레시피를 작성했습니다.", recipe.getMemId());
            PubSubServer.sendMessageToSession(follow.getFollowerId(), message);
        }
    }
	
	
}
