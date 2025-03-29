$(document).ready(function() {
	// 레시피들
	let offset = 0;
	const limit = 10;
	let loading = false;

	if (loading) return;
	loading = true;

	fetch(`/api/recipes?offset=${offset}&limit=${limit}`)
		.then(response => response.json())
		.then(data => {
			if (data.length > 0) {
				offset += limit;
			}
			loading = false;
		})
		.catch(error => {
			loading = false;
		});
});

// 무한 스크롤 이벤트
window.addEventListener("scroll", () => {
	if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
	}
});
