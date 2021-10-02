package smu.earthranger.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smu.earthranger.dto.tree.TreeMemberResponseDto;
import smu.earthranger.dto.tree.TreeResponseDto;
import smu.earthranger.jwt.SecurityUtil;
import smu.earthranger.service.TreeService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
public class TreeController {

    private final TreeService treeService;

    //본인 식물 조회
    @GetMapping
    public ResponseEntity<TreeResponseDto> findMainPlant(){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        TreeResponseDto selfTreeDto = treeService.findSelfTree(userId.get());
        return ResponseEntity.ok(selfTreeDto);
    }

    //이웃 식물 조회(다이얼로그)
    @GetMapping("/{memberId}")
    public ResponseEntity<TreeMemberResponseDto> findMemberPlant(@PathVariable("memberId") Long memberId){
        TreeMemberResponseDto memberTreeDto = treeService.findMemberTree(memberId);
        return ResponseEntity.ok(memberTreeDto);
    }

}
