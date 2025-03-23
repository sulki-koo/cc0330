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


let offset = 0;
const limit = 10;
const memId = document.getElementById("memId").value;
let loading = false;
let currentType = "recipes"; // 기본값

document.querySelectorAll(".tab-button").forEach(button => {
    button.addEventListener("click", function () {
        document.querySelectorAll(".tab-button").forEach(btn => btn.classList.remove("active"));
        this.classList.add("active");

        document.querySelectorAll(".content-tab").forEach(tab => tab.classList.remove("active"));
        document.getElementById(`${this.dataset.type}-container`).classList.add("active");

        currentType = this.dataset.type; // 현재 선택된 탭 타입 변경
        offset = 0; // 새로운 데이터 로딩을 위해 offset 초기화
        document.getElementById(`${currentType}-container`).innerHTML = ""; // 기존 데이터 삭제
        loadMoreData(currentType);
    });
});

function loadMoreData(type) {
    if (loading) return;
    loading = true;

    fetch(`/api/mypage/${type}?memId=${memId}&offset=${offset}&limit=${limit}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                renderData(type, data);
                offset += limit;
            }
            loading = false;
        })
        .catch(error => {
            console.error("Error loading data:", error);
            loading = false;
        });
}

function renderData(type, data) {
    const container = document.getElementById(`${type}-container`);
    data.forEach(item => {
        const div = document.createElement("div");
        div.classList.add("item");
        div.innerHTML = `<p>${item.title || item.name || item.content}</p>`;
        container.appendChild(div);
    });
}

// 무한 스크롤 이벤트
window.addEventListener("scroll", () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        loadMoreData(currentType); // 현재 선택된 탭의 타입으로 데이터 요청
    }
});
