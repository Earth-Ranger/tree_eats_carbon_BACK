package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.follow.FollowResponseDto;
import smu.earthranger.jwt.SecurityUtil;
import smu.earthranger.service.FollowService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    //follow/search/?option=0&value="semi"
    @GetMapping("/search")
    public ResponseEntity<FollowResponseDto> searchFollower(
            @RequestParam("option") int option,
            @RequestParam("value") String value){

        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        FollowResponseDto dto = followService.findFollowerByOption(userId.get(), option, value);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<?> searchFollowList(){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        List<FollowResponseDto> followList = followService.getFollowList(userId.get());
        return ResponseEntity.ok(followList);
    }


    @PostMapping("/{memberId}")
    public ResponseEntity<ResponseMessage> addFollower(@PathVariable("memberId") Long memberId){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        followService.followMember(userId.get(), memberId);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED, "ok"));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ResponseMessage> deleteFollower(@PathVariable("memberId") Long memberId){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        followService.unfollowMember(userId.get(), memberId);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.NO_CONTENT, "ok"));
    }

}
