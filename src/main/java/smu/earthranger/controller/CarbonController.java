package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.dto.ResponseMessage;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.dto.carbon.CarbonResponseInfoDto;
import smu.earthranger.jwt.SecurityUtil;
import smu.earthranger.service.CarbonService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carbon")
public class CarbonController {

    private final CarbonService carbonService;


    @GetMapping
    public ResponseEntity<CarbonResponseInfoDto> getInfo(){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        CarbonResponseInfoDto info = carbonService.getInfo(userId.get());
        return ResponseEntity.ok(info);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> saveCarbon(@RequestBody CarbonRequestDto carbon){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        carbonService.saveCarbon(userId.get(), carbon);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.CREATED,"ok"));
    }
}
