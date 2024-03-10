package meoipzi.meoipzi.scrap.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.scrap.dto.ScrapRequestDto;
import meoipzi.meoipzi.scrap.service.ScrapService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping(value = {"/outfits/{contentId}/scrap", "/products/{contentId}/scrap"})
    public ResponseEntity<?> scrap(@PathVariable(name = "contentId") Long contentId, @RequestBody @Valid ScrapRequestDto scrapRequestDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        scrapRequestDto.setUsername(authentication.getName());
        scrapRequestDto.setContentId(contentId);
        return scrapService.scrapContent(scrapRequestDto);
    }
}