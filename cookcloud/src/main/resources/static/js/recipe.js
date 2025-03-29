$(document).ready(function() {
	// 검색 버튼 클릭 시 해시태그 검색
	$('#hashtagSearch').click(function() {
		const hashtag = $('#hashtagSearch').val();
		console.log("Searching for Hashtag:", hashtag);
		// 해시태그에 맞는 레시피를 필터링하는 로직
		searchByHashtag(hashtag);
	});
	
	// 검색 입력 필드에 따른 레시피 검색 처리
	$('#searchInput').keyup(function() {
		const searchTerm = $(this).val();
		console.log("Searching for term:", searchTerm);
		// 검색어에 맞는 레시피를 필터링하는 로직
		searchRecipes(searchTerm);
	});

	// 이미지 미리보기 기능
	$('#image').on('change', function(e) {
		let reader = new FileReader();
		reader.onload = function(e) {
			let image = new Image();
			image.src = e.target.result;
			// 여기에서 이미지 미리보기 설정을 추가할 수 있습니다.
		}
		reader.readAsDataURL(this.files[0]);
	});
	
});


