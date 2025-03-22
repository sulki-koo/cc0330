package cookcloud.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Follows;
import cookcloud.entity.Likes;
import cookcloud.entity.Member;
import cookcloud.entity.Message;
import cookcloud.entity.Recipe;
import cookcloud.entity.Review;
import cookcloud.repository.FollowsRepository;
import cookcloud.repository.LikesRepository;
import cookcloud.repository.MessageRepository;
import cookcloud.repository.RecipeRepository;
import cookcloud.repository.ReviewRepository;

@Service
public class MyPageService {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private FollowsRepository followsRepository;
    
    @Autowired
    private LikesRepository likesRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    // 내 레시피 목록 조회
    public List<Recipe> getMyRecipes(String memId) {
    	List<Recipe> recipes = recipeRepository.findByMemberMemId(memId);
        
        // 각 레시피에 첫 번째 첨부파일 URL을 설정
        for (Recipe recipe : recipes) {
            if (!recipe.getAttachList().isEmpty()) {
                recipe.setImageUrl(recipe.getAttachList().get(0).getAttachServerName());
            }
        }

        return recipes;
    }
    
    // 내가 구독한(팔로잉) 사용자 목록 조회
    public List<Member> getMyFollowings(String memId) {
        return followsRepository.findByFollowerId(memId)
                .stream().map(Follows::getFollowing).collect(Collectors.toList());
    }
    
    // 나를 구독중인(팔로워) 사용자 목록 조회
    public List<Member> getMyFollowers(String memId) {
        return followsRepository.findByFollowingId(memId)
                .stream().map(Follows::getFollower).collect(Collectors.toList());
    }
    
    // 좋아요한 레시피 조회
    public List<Recipe> getLikedRecipes(String memId) {
        return likesRepository.findByMemberMemId(memId)
                .stream().map(Likes::getRecipe).collect(Collectors.toList());
    }
    
    // 내가 작성한 리뷰 조회
    public List<Review> getMyReviews(String memId) {
        return reviewRepository.findByMemberMemId(memId);
    }
    
    // 메시지 목록 조회
    public List<Message> getMessages(String memId) {
        return messageRepository.findByMemberMemId(memId);
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
    }
}
