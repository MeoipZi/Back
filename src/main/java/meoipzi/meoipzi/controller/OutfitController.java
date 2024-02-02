package meoipzi.meoipzi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.dto.OutfitRequestDTO;
import meoipzi.meoipzi.dto.OutfitResponseDTO;
import meoipzi.meoipzi.service.OutfitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OutfitController {
    private final OutfitService outfitService;

    //코디 등록하기!
    @PostMapping("/outfits")
    public void saveOutfit(OutfitRequestDTO outfitRequestDTO){
        /*Outfit outfit = new Outfit();
        outfit.setImgUrl(outfitRequestDTO.getImgUrl());*/
        outfitService.join(outfitRequestDTO);
    }
    //코디 수정하기 필요한가???? -> 등록할때 필요할 수 도
    //코디 삭제하기 필요한가????? -> 필요할 듯!

    // [사진 클릭 부분] 1개 코디와 3개의 상품 조회
    @GetMapping("/outfits/{outfitId}")
    public OutfitResponseDTO clickOutfit(@PathVariable("outfitId") Long outfitId){
        return outfitService.clickOutfit(outfitId);
    }

}
