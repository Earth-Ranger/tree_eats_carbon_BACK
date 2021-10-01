package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import smu.earthranger.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.member.MemberLoginDto;
import smu.earthranger.dto.member.MemberSignupDto;
import smu.earthranger.dto.user.TokenDto;
import smu.earthranger.service.MemberService;

import javax.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        memberService.save(memberSignupDto);
        if(bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
        }
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED, "Registered successfully!"));
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody MemberLoginDto memberLoginDto){
        return memberService.getToken(memberLoginDto);
    }

}