package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smu.earthranger.dto.carbon.CarbonDto;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.dto.carbon.CarbonResponseInfoDto;
import smu.earthranger.jwt.SecurityUtil;
import smu.earthranger.service.CarbonService;
import smu.earthranger.service.InfoService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carbon")
public class CarbonController {

    private final CarbonService carbonService;
    private final InfoService infoService;

    @GetMapping
    public ResponseEntity<CarbonResponseInfoDto> getInfo(){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        CarbonResponseInfoDto info = infoService.getInfo(userId.get());
        return ResponseEntity.ok(info);
    }

    @PostMapping
    public ResponseEntity<CarbonDto> saveCarbon(@RequestBody CarbonRequestDto carbon){
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        CarbonDto carbonDto = carbonService.saveCarbon(userId.get(), carbon);
        return ResponseEntity.ok(carbonDto);
    }
}
