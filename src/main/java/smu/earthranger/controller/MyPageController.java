package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.member.MemberUpdateDto;
import smu.earthranger.dto.user.MyPageResponseDto;
import smu.earthranger.jwt.SecurityUtil;
import smu.earthranger.service.MyPageService;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<?> getMyPage() {
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        MyPageResponseDto myPage = myPageService.getMyPage(userId.get());
        return ResponseEntity.ok(myPage);
    }

    @PutMapping
    public ResponseEntity<ResponseMessage> update(@RequestBody @Valid MemberUpdateDto memberUpdateDto){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        myPageService.updateMyPage(userId.get(),memberUpdateDto);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"Updated successfully!"));
    }
}
