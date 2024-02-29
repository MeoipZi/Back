package MeoipZi.loginMeoipZi.controller;

import MeoipZi.loginMeoipZi.service.MyPageService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    //내가 작성한 post - 피드 탭
    @GetMapping("/posts/feeds")
    public ResponseEntity<?> postedFeeds(Principal principal) throws IOException {
        return myPageService.getFeeds(principal);
    }

    //내가 등록한 post - 스크랩 탭
    @GetMapping("/posts/scraps")
    public ResponseEntity<?> postedScrap(Principal principal) throws IOException {
        return myPageService.getScraps(principal);
    }

    //내가 작성한 post - 댓글 탭
    @GetMapping("/posts/comments")
    public ResponseEntity<?> postedComments(Principal principal) throws IOException {
        return myPageService.getComments(principal);
    }

    //내가 like (코디, 숏폼)
    @GetMapping("/likes")
    public ResponseEntity<?> likedContents(Principal principal) throws IOException {
        return myPageService.getLikes(principal);
    }
}
