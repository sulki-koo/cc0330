// 메시지 읽음 처리
function markMessageRead(messageId) {
	fetch('/mypage/message/' + messageId + '/read', {
		method: 'POST'
	}).then(response => {
		if (response.ok) {
			alert('메시지 읽음 처리되었습니다.');
		}
	});
}

// 메시지 삭제
function deleteMessage(messageId) {
	fetch('/mypage/message/' + messageId, {
		method: 'DELETE'
	}).then(response => {
		if (response.ok) {
			alert('메시지가 삭제되었습니다.');
			location.reload();
		}
	});
}