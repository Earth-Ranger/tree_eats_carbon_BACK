package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smu.earthranger.dto.user.MemberRankingResponseDto;
import smu.earthranger.jwt.SecurityUtil;
import smu.earthranger.service.RankService;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
public class RankController {

    private final RankService rankService;


    @GetMapping
    public ResponseEntity<?> getRank(@RequestParam String user,
                                     @PageableDefault(size = 20, sort = "totalReduction", direction = Sort.Direction.DESC) Pageable pageable){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        Page<MemberRankingResponseDto> dto = rankService.getByPermission(userId.get(), user, pageable);
        return ResponseEntity.ok(dto);
    }

}