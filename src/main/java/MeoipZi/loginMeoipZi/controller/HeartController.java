package MeoipZi.loginMeoipZi.controller;

import MeoipZi.loginMeoipZi.dto.HeartRequestDto;
import MeoipZi.loginMeoipZi.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeartController {

    private final HeartService heartService;

    @PostMapping(value = {"/outfits/{contentId}/like", "/shortforms/{contentId}/like", "/community/{contentId}/like"})
    public ResponseEntity<?> like(@PathVariable(name = "contentId") Long contentId, @RequestBody @Valid HeartRequestDto heartRequestDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        heartRequestDto.setUsername(authentication.getName());
        heartRequestDto.setContentId(contentId);
        return heartService.likeHeart(heartRequestDto);
    }
}
