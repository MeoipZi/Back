package meoipzi.meoipzi.mypage.service;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import meoipzi.meoipzi.comment.repository.CommentCommunityRepository;
import meoipzi.meoipzi.comment.repository.CommentOutfitRepository;
import meoipzi.meoipzi.comment.repository.CommentShortFormRepository;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.community.repository.CommunityRepository;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.heart.repository.HeartRepository;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.mypage.dto.*;
import meoipzi.meoipzi.common.excepiton.NotFoundContentException;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.product.domain.Product;
import meoipzi.meoipzi.scrap.domain.Scrap;
import meoipzi.meoipzi.scrap.repository.ScrapRepository;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import meoipzi.meoipzi.shortform.repository.ShortFormRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final ShortFormRepository shortFormRepository;
    private final ScrapRepository scrapRepository;
    private final HeartRepository heartRepository;
    private final CommentOutfitRepository cmtOutfitRepository;
    private final CommentShortFormRepository cmtSFRepository;
    private final CommentCommunityRepository cmtCommRepository;

    @Transactional
    public ResponseEntity<?> getFeeds(Principal principal) throws IOException {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Community> commList = communityRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        List<ShortForm> sfList = shortFormRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        if(commList.isEmpty()&&sfList.isEmpty()){
            throw new NotFoundContentException("Could not find community board for user: " + username);
        }

        log.info("community 게시글: {}", commList.isEmpty());
        log.info("shortforms: {}", sfList.isEmpty());

        //현재까지 작성한 피드(커뮤니티, 숏폼)의 정보가 저장될 dto 리스트
        List<MyCommResponseDto> uploadedComms = new ArrayList<>();
        List<MyImageResponseDto> uploadedSFs = new ArrayList<>();
        MyFeedsDto uploadedFeeds = new MyFeedsDto();

        if(!commList.isEmpty()) {
            for (Community comm : commList) {
                MyCommResponseDto uploadedComm = new MyCommResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCommentCounts(), comm.getCreatedAt());
                uploadedComms.add(uploadedComm);
            }
        }
        if(!sfList.isEmpty()) {
            for (ShortForm sf : sfList) {
                MyImageResponseDto uploadedSF = new MyImageResponseDto(sf.getId(), sf.getImgUrl(), sf.getCreatedAt());
                uploadedSFs.add(uploadedSF);
            }
        }

        uploadedFeeds.setUploadedComms(uploadedComms);
        uploadedFeeds.setUploadedSFs(uploadedSFs);
        return new ResponseEntity<>(uploadedFeeds, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getComments(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        //load 3 comments - outfit, community, shortform, reply
        List<CommentOutfit> cmtOutfitList = cmtOutfitRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        List<CommentShortForm> cmtSFList = cmtSFRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        List<CommentCommunity> cmtCommList = cmtCommRepository.findTop3DistinctCommentsByUserOrderByCreatedAtDesc(user);

        if(cmtOutfitList.isEmpty()&&cmtSFList.isEmpty()&&cmtCommList.isEmpty()){
            throw new NotFoundContentException("Could not find comments for user: " + username);
        }
        log.info("outfits 댓글: {}", cmtOutfitList.isEmpty());
        log.info("shortforms 댓글: {}", cmtSFList.isEmpty());
        log.info("comms 댓글: {}", cmtCommList.isEmpty());

        List<MyImageResponseDto> cmtOutfits = new ArrayList<>();
        List<MyImageResponseDto> cmtShortForms = new ArrayList<>();
        List<MyCommResponseDto> cmtComms = new ArrayList<>();
        MyCommentsDto myComments = new MyCommentsDto();


        if(!cmtOutfitList.isEmpty()){
            for(CommentOutfit cmt: cmtOutfitList){
                Outfit outfit = cmt.getOutfit();
                MyImageResponseDto cmtOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), cmt.getCreateDateTime());
                cmtOutfits.add(cmtOutfit);
            }
        }
        if(!cmtSFList.isEmpty()){
            for(CommentShortForm cmt: cmtSFList){
                ShortForm shortForm = cmt.getShortForm();
                MyImageResponseDto cmtShortForm = new MyImageResponseDto(shortForm.getId(), shortForm.getImgUrl(), cmt.getCreateDateTime());
                cmtShortForms.add(cmtShortForm);
            }
        }
        if(!cmtCommList.isEmpty()){
            for(CommentCommunity cmt: cmtCommList){
                Community comm = cmt.getCommunity();
                MyCommResponseDto cmtComm = new MyCommResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCommentCounts(), cmt.getCreateDateTime());
                cmtComms.add(cmtComm);
            }
        }
        myComments.setCmtOutfits(cmtOutfits);
        myComments.setCmtShortforms(cmtShortForms);
        myComments.setCmtComms(cmtComms);
        return new ResponseEntity<>(myComments, HttpStatus.OK);
    }

    //outfit, product
    @Transactional
    public ResponseEntity<?> getScraps(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Scrap> outfitList = scrapRepository.findTop3ByUserAndOutfitNotNullOrderByCreatedAtDesc(user);
        List<Scrap> productList = scrapRepository.findTop3ByUserAndProductNotNullOrderByCreatedAtDesc(user);
        if(outfitList.isEmpty()&&productList.isEmpty()){
            throw new NotFoundContentException("Could not find scraps for user: " + username);
        }

        log.info("스크랩한 outfits: {}", outfitList.isEmpty());
        log.info("스크랩한 products: {}", productList.isEmpty());

        List<MyImageResponseDto> scrapedOutfits = new ArrayList<>();
        List<MyImageResponseDto> scrapedProducts = new ArrayList<>();
        MyScrapsDto bookmarks = new MyScrapsDto();

        if(!outfitList.isEmpty()){
            for(Scrap scrap: outfitList){
                Outfit outfit = scrap.getOutfit();
                MyImageResponseDto scrapedOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), scrap.getCreatedAt());
                scrapedOutfits.add(scrapedOutfit);
            }
        }
        if(!productList.isEmpty()){
            for(Scrap scrap: productList){
                Product product = scrap.getProduct();
                MyImageResponseDto scrapedProduct = new MyImageResponseDto(product.getId(), product.getImgUrl(), scrap.getCreatedAt());
                scrapedProducts.add(scrapedProduct);
            }
        }

        bookmarks.setScrapedOutfits(scrapedOutfits);
        bookmarks.setScrapedProducts(scrapedProducts);
        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getLikes(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Heart> outfitList = heartRepository.findTop3ByUserAndOutfitNotNullOrderByCreatedAtDesc(user);
        List<Heart> commList = heartRepository.findTop3ByUserAndCommunityNotNullOrderByCreatedAtDesc(user);
        List<Heart> shortFormList = heartRepository.findTop3ByUserAndShortFormNotNullOrderByCreatedAtDesc(user);

        if(outfitList.isEmpty()&&shortFormList.isEmpty()&&commList.isEmpty()){
            throw new NotFoundContentException("Could not find likes for user: " + username);
        }
        log.info("좋아요 outfits: {}", outfitList.isEmpty());
        log.info("좋아요 shortforms: {}", shortFormList.isEmpty());
        log.info("좋아요 comms: {}", commList.isEmpty());

        List<MyImageResponseDto> likedOutfits = new ArrayList<>();
        List<MyImageResponseDto> likedShortForms = new ArrayList<>();
        List<MyCommResponseDto> likedComms = new ArrayList<>();
        MyHeartsDto likeHearts = new MyHeartsDto();

        if(!outfitList.isEmpty()){
            for(Heart heart: outfitList){
                Outfit outfit = heart.getOutfit();
                MyImageResponseDto likedOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), heart.getCreatedAt());
                likedOutfits.add(likedOutfit);
            }
        }
        if(!shortFormList.isEmpty()){
            for(Heart heart: shortFormList){
                ShortForm shortForm = heart.getShortForm();
                MyImageResponseDto likedShortForm = new MyImageResponseDto(shortForm.getId(), shortForm.getImgUrl(), heart.getCreatedAt());
                likedShortForms.add(likedShortForm);
            }
        }
        if(!commList.isEmpty()){
            for(Heart heart: commList){
                Community comm = heart.getCommunity();
                MyCommResponseDto likedComm = new MyCommResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCommentCounts(), heart.getCreatedAt());
                likedComms.add(likedComm);
            }
        }
        likeHearts.setLikedOutfits(likedOutfits);
        likeHearts.setLikedShortForms(likedShortForms);
        likeHearts.setLikedComms(likedComms);
        return new ResponseEntity<>(likeHearts, HttpStatus.OK);
    }
}