package cookcloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Review;
import cookcloud.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
    private ReviewRepository reviewRepository;

	// 내가 작성한 리뷰 조회
    public List<Review> getMyReviews(String memId) {
        return reviewRepository.findByMemId(memId);
    }

	public List<Review> getReviews(Long recipeId) {
		return reviewRepository.findByRecipeId(recipeId);
	}
	
	public Optional<Review> getReview(Long reviewId){
		return reviewRepository.findByReviewId(reviewId);
	}

}
