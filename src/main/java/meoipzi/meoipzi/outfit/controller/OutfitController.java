package meoipzi.meoipzi.outfit.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.outfit.dto.OutfitRequestDTO;
import meoipzi.meoipzi.outfit.dto.OutfitResponseDTO;
import meoipzi.meoipzi.outfit.dto.OutfitUpdateRequestDTO;
import meoipzi.meoipzi.outfit.service.OutfitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OutfitController {
    private final OutfitService outfitService;

    //코디 등록하기!
    @PostMapping("/outfits")
    public void saveOutfit(OutfitRequestDTO outfitRequestDTO){
        System.out.println("뭐지");
        outfitService.join(outfitRequestDTO);
    }
    //코디 수정하기 필요한가???? -> 등록할때 필요할 수 도
    @PatchMapping("/outfits/{outfitId}")
    public void updateOutfit(@PathVariable("outfitId")Long outfitId, OutfitUpdateRequestDTO outfitUpdateRequestDTO){
        outfitService.updateOutfit(outfitId, outfitUpdateRequestDTO);
    }
    //코디 삭제하기 필요한가????? -> 필요할 듯! 근데 product랑 연결땜에 안됨..ㅜㅜ

    // [사진 클릭 부분] 1개 코디와 3개의 상품 조회
    @GetMapping("/outfits/{outfitId}")
    public OutfitResponseDTO clickOutfit(@PathVariable("outfitId") Long outfitId){
        return outfitService.clickOutfit(outfitId);
    }

}