package cookcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cookcloud.service.MemberService;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";  // signup.html 페이지 반환
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String registerMember(@RequestParam String memId, 
                                 @RequestParam String memPassword,
                                 @RequestParam String memName,
                                 @RequestParam String memNickname,
                                 @RequestParam String memEmail,
                                 @RequestParam String memPhone,
                                 Model model) {
        try {
            memberService.registerMember(memId, memPassword, memName, memNickname, memEmail, memPhone);
            model.addAttribute("message", "회원가입 성공! 로그인하세요.");
            return "login";  // 회원가입 후 로그인 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 실패: " + e.getMessage());
            return "signup";  // 실패 시 다시 회원가입 페이지로 이동
        }
    }
}
