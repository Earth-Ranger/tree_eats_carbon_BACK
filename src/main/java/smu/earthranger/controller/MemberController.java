package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.member.MemberSignupDto;
import smu.earthranger.dto.member.MemberUpdateDto;
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


    /**
    @PatchMapping("/update")
    public ResponseEntity<ResponseMessage> update(@RequestBody @Valid MemberUpdateDto memberUpdateDto, MemberSignupDto memberSignupDto){
        memberService.update(memberUpdateDto,memberSignupDto);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"Updated successfully!"));
    }
     **/

    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.NO_CONTENT, "ok"));
    }


}
