package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 로그아웃
    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.NO_CONTENT, "ok"));
    }


}
