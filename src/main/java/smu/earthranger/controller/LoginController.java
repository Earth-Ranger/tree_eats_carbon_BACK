package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import smu.earthranger.service.MemberService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    // 회원 가입 페이지로 이동
    @GetMapping("/signup")
    public String signup() {
        return "/signup";
    }

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    // 회원 정보 수정 페이지로 이동
    @GetMapping("member/update")
    public String update() {
        return "member/update";
    }
}
