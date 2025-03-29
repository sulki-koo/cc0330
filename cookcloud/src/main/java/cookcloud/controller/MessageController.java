package cookcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Message;
import cookcloud.service.MessageService;

@Controller
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	// 메시지 읽음 처리
	@PostMapping("/mypage/message/{id}/read")
	@ResponseBody
	public ResponseEntity<Message> markMessageAsRead(@PathVariable Long messageId) {
		messageService.markMessageAsRead(messageId);
		return ResponseEntity.ok().build();
	}

	// 메시지 삭제 처리
	@PutMapping("/mypage/message/{id}")
	@ResponseBody
	public ResponseEntity<Message> deleteMessage(@PathVariable Long messageId) {
		messageService.deleteMessage(messageId);
		return ResponseEntity.ok().build();
	}

}
