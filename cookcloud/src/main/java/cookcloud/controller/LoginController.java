package cookcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // 로그인 페이지로 리다이렉트
        return "login";  // login.html 페이지를 렌더링
    }
}
