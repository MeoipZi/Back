package meoipzi.meoipzi.mypage.controller;


import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.mypage.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    //내가 작성한 post - 피드 탭
    @GetMapping("/posts/feeds")
    public ResponseEntity<?> postedFeeds(Principal principal) throws IOException {
        return myPageService.getFeeds(principal);
    }
    // post - 피드 - 커뮤니티 더보기
    @GetMapping("/posts/feeds/communities")
    public ResponseEntity<?> postedComms(Principal principal) throws IOException {
        return myPageService.getComms(principal);
    }
    // post - 피드 - 숏폼 더보기
    @GetMapping("/posts/feeds/shortforms")
    public ResponseEntity<?> postedShortForms(Principal principal) throws IOException {
        return myPageService.getShortForms(principal);
    }

    //내가 작성한 post - 댓글 탭
    @GetMapping("/posts/comments")
    public ResponseEntity<?> postedComments(Principal principal) throws IOException {
        return myPageService.getComments(principal);
    }
    // post - 댓글 - 커뮤니티 더보기
    @GetMapping("/posts/comments/communities")
    public ResponseEntity<?> cmtsComms(Principal principal) throws IOException {
        return myPageService.getCmtComms(principal);
    }
    // post - 댓글 - 코디 더보기
    @GetMapping("/posts/comments/outfits")
    public ResponseEntity<?> cmtsOutfits(Principal principal) throws IOException {
        return myPageService.getCmtOutfits(principal);
    }
    // post - 댓글 - 숏폼 더보기
    @GetMapping("/posts/comments/shortforms")
    public ResponseEntity<?> cmtsShortforms(Principal principal) throws IOException {
        return myPageService.getCmtSFs(principal);
    }

    //내가 등록한 post - 스크랩 탭
    @GetMapping("/posts/scraps")
    public ResponseEntity<?> postedScrap(Principal principal) throws IOException {
        return myPageService.getScraps(principal);
    }
    // post - 스크랩 - 코디
    @GetMapping("/posts/scraps/outfits")
    public ResponseEntity<?> scrapsOutfits(Principal principal) throws IOException {
        return myPageService.getSrpOutfits(principal);
    }
    // post - 스크랩 - 상품
    @GetMapping("/posts/scraps/products")
    public ResponseEntity<?> scrapsProducts(Principal principal) throws IOException {
        return myPageService.getSrpProducts(principal);
    }


    //내가 like (코디, 숏폼)
    @GetMapping("/likes")
    public ResponseEntity<?> likedContents(Principal principal) throws IOException {
        return myPageService.getLikes(principal);
    }
    // like - 코디 더보기
    @GetMapping("/likes/outfits")
    public ResponseEntity<?> likedOutfits(Principal principal) throws IOException {
        return myPageService.getLikeOutfits(principal);
    }
    // like - 숏폼 더보기
    @GetMapping("/likes/shortforms")
    public ResponseEntity<?> likedShortforms(Principal principal) throws IOException {
        return myPageService.getLikeSFs(principal);
    }

    /*shortform*/
}