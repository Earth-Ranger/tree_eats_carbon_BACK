package smu.earthranger.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.config.ResponseMessage;
import smu.earthranger.config.auth.MemberDetails;
import smu.earthranger.dto.MemberSignupDto;
import smu.earthranger.dto.MemberUpdateDto;
import smu.earthranger.service.MemberService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        memberService.save(memberSignupDto);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED, "Registered successfully!"));
    }

    // 회원 정보 수정
    @PutMapping("member/update")
    public ResponseEntity update(@RequestBody @Valid MemberUpdateDto memberUpdateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal MemberDetails memberDetails) {
        memberService.update(memberUpdateDto,memberDetails);
        redirectAttributes.addAttribute("id", memberDetails.getMember().getId());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"Updated successfully!"));
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.NO_CONTENT, "ok"));
    }


    /**
     //회원가입
     @PostMapping("/signup")
     public String signup(@Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
     memberService.save(memberSignupDto);
     return "redirect:/";
     }

     // 회원 정보 수정
     @PutMapping("/member/update")
     public String update(@Valid MemberUpdateDto memberUpdateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal MemberDetails memberDetails) {
     memberService.update(memberUpdateDto,memberDetails);
     redirectAttributes.addAttribute("id", memberDetails.getMember().getId());
     return "redirect:/";
     }

     //로그아웃
     @GetMapping(value = "/logout")
     public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
     new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
     return "redirect:/";
     }
     **/
}