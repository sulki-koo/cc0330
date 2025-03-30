$(document).ready(function() {
	const recipeId = $("#recipeId").val();

	// 팔로우 버튼 클릭
	$("#followBtn").click(function() {
		$.post(`/recipes/follow/${recipeId}`, function(response) {
			if (response.followed) {
				$("#followIcon").removeClass("bi-bell").addClass("bi-bell-fill text-warning");
				location.reload();
			} else {
				$("#followIcon").removeClass("bi-bell-fill text-warning").addClass("bi-bell");
				location.reload();
			}
		});
	});

	// 리뷰 작성
	$("#reviewForm").submit(function(e) {
		e.preventDefault();
		const content = $("#reviewContent").val();

		$.post("/reviews/create", { recipeId, content }, function(response) {
			$("#reviewContent").val(""); // 입력창 비우기
			$("#reviews").prepend(`<p>${response.content} (새 리뷰)</p>`);
		});
	});

});


function toggleLike(button, type) {
    const itemId = $(button).data("recipe-id"); // 레시피 ID를 받아옴
    $.post("/likes/liked", {
        type: type,  // 'recipe' 또는 'review'
        itemId: itemId  // 해당 아이템의 ID
    }, function(response) {
        // 서버에서 좋아요 처리 결과가 오면 하트 아이콘 상태 변경
        if (response === "좋아요 추가") {
            $(button).find("i").removeClass("bi-heart").addClass("bi-heart-fill text-danger");
			location.reload();
        } else {
            $(button).find("i").removeClass("bi-heart-fill text-danger").addClass("bi-heart");
			location.reload();
        }
    });
}


function showReportPopup(targetId, type) {
	document.getElementById('targetId').value = targetId;
	document.getElementById('reportType').value = type;

	let form = document.getElementById('reportForm');
	form.action = `/report/${type}/${targetId}`;

	// 팝업과 오버레이 표시 + 부드러운 애니메이션
	document.getElementById('reportOverlay').style.display = 'block';
	let modal = document.getElementById('reportModal');
	modal.style.display = 'block';
	setTimeout(() => {
		modal.style.opacity = '1';
	}, 10);
}

// 폼 제출을 AJAX로 변경
document.getElementById("reportForm").addEventListener("submit", function(event) {
	event.preventDefault(); // 기본 제출 동작 막기

	let formData = new FormData(this);
	let targetId = document.getElementById('targetId').value;
	let type = document.getElementById('reportType').value;

	fetch(`/report/${type}/${targetId}`, {
		method: "POST",
		body: formData
	})
		.then(response => {
			if (response.ok) {
				alert("신고가 접수되었습니다.");
				closeReportPopup();
			} else {
				alert("신고 처리 중 오류가 발생했습니다.");
			}
		})
		.catch(error => console.error("Error:", error));
});

function closeReportPopup() {
	let modal = document.getElementById('reportModal');
	modal.style.opacity = '0';
	setTimeout(() => {
		modal.style.display = 'none';
		document.getElementById('reportOverlay').style.display = 'none';
	}, 300);
}

