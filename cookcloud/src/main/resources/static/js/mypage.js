function markMessageRead(messageId) {
    fetch('/mypage/message/' + messageId + '/read', {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            alert('메시지 읽음 처리되었습니다.');
            // 필요시 UI 업데이트
        }
    });
}

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

document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".tab-button");
    const tabs = document.querySelectorAll(".content-tab");

    // 기본적으로 '내 레시피'는 보이도록 설정
    document.getElementById("my-recipes").classList.add("active");

    buttons.forEach(button => {
        button.addEventListener("click", function () {
            const targetId = this.getAttribute("data-target");

            // 모든 탭 숨기기
            tabs.forEach(tab => tab.classList.remove("active"));

            // 선택한 탭만 표시
            document.getElementById(targetId).classList.add("active");
        });
    });
});
