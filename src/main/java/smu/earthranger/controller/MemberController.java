package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.member.MemberLoginDto;
import smu.earthranger.dto.member.MemberSignupDto;
import smu.earthranger.dto.user.TokenDto;
import smu.earthranger.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

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

    //중복확인
    @GetMapping
    public ResponseEntity<?> checkName(@RequestParam("name") String name){
        return new ResponseEntity<>(memberService.checkName(name), HttpStatus.OK);
    }

}
