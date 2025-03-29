package cookcloud.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Member;
import cookcloud.entity.Message;
import cookcloud.entity.Recipe;
import cookcloud.entity.Review;
import cookcloud.service.FollowsService;
import cookcloud.service.LikesService;
import cookcloud.service.MemberService;
import cookcloud.service.MessageService;
import cookcloud.service.RecipeService;
import cookcloud.service.ReviewService;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private FollowsService followsService;

	@Autowired
	private LikesService likesService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MessageService messageService;

	@GetMapping
	public String viewMyPage(Model model, Principal principal) {
		
		Member member = memberService.getMember(principal.getName()).get();
		String memId = member.getMemId();

		// 필요한 데이터 조회
		List<Recipe> myRecipes = recipeService.getMyRecipes(memId);
		List<Member> followings = followsService.getMyFollowings(memId);
		List<Member> followers = followsService.getMyFollowers(memId);
		List<Recipe> likedRecipes = likesService.getLikedRecipes(memId);
		List<Review> myReviews = reviewService.getMyReviews(memId);
		List<Message> messages = messageService.getMessages(memId);

		// 모델에 데이터 전달
		model.addAttribute("member", member);
		model.addAttribute("myRecipes", myRecipes);
		model.addAttribute("followings", followings);
		model.addAttribute("followers", followers);
		model.addAttribute("likedRecipes", likedRecipes);
		model.addAttribute("myReviews", myReviews);
		model.addAttribute("messages", messages);

		return "mypage/main"; // Thymeleaf 템플릿 이름
	}

}
