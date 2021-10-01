package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.member.MemberUpdateDto;
import smu.earthranger.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
    @PutMapping("member/update")
    public ResponseEntity update(@RequestBody @Valid MemberUpdateDto memberUpdateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal MemberDetails memberDetails) {
        memberService.update(memberUpdateDto,memberDetails);
        redirectAttributes.addAttribute("id", memberDetails.getMember().getId());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"Updated successfully!"));
    }
*/
    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.NO_CONTENT, "ok"));
    }


}
