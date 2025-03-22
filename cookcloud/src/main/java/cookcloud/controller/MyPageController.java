package cookcloud.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Member;
import cookcloud.entity.Message;
import cookcloud.entity.Recipe;
import cookcloud.entity.Review;
import cookcloud.repository.MemberRepository;
import cookcloud.service.Impl.MyPageService;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @GetMapping
    public String viewMyPage(Model model, Principal principal) {
        // 로그인된 사용자(memNickname 기반) 조회
        Member member = memberRepository.findByMemNickname(principal.getName())
                .orElseThrow(() -> new RuntimeException("회원 확인 불가"));
        
        String memId = member.getMemId();
        
        // 필요한 데이터 조회
        List<Recipe> myRecipes = myPageService.getMyRecipes(memId);
        List<Member> followings = myPageService.getMyFollowings(memId);
        List<Member> followers = myPageService.getMyFollowers(memId);
        List<Recipe> likedRecipes = myPageService.getLikedRecipes(memId);
        List<Review> myReviews = myPageService.getMyReviews(memId);
        List<Message> messages = myPageService.getMessages(memId);
        
        // 모델에 데이터 전달
        model.addAttribute("member", member);
        model.addAttribute("myRecipes", myRecipes);
        model.addAttribute("followings", followings);
        model.addAttribute("followers", followers);
        model.addAttribute("likedRecipes", likedRecipes);
        model.addAttribute("myReviews", myReviews);
        model.addAttribute("messages", messages);
        
        return "mypage/main";  // Thymeleaf 템플릿 이름
    }
    
    // AJAX: 메시지 읽음 처리
    @PostMapping("/message/{id}/read")
    @ResponseBody
    public ResponseEntity<?> markMessageAsRead(@PathVariable Long id) {
        myPageService.markMessageAsRead(id);
        return ResponseEntity.ok().build();
    }
    
    // AJAX: 메시지 삭제 처리
    @DeleteMapping("/message/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        myPageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }
}
