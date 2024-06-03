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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // get feeds (community, shortform)
    @Transactional
    public ResponseEntity<?> getFeeds(Principal principal) throws IOException {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Community> commList = communityRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        List<ShortForm> sfList = shortFormRepository.findTop3ByUserOrderByCreatedAtDesc(user);

        //현재까지 작성한 피드(커뮤니티, 숏폼)의 정보가 저장될 dto 리스트
        List<MyCommResponseDto> uploadedComms = new ArrayList<>();
        List<MyImageResponseDto> uploadedSFs = new ArrayList<>();
        MyFeedsDto uploadedFeeds = new MyFeedsDto();

        if(!commList.isEmpty()) {
            for (Community comm : commList) {
                MyCommResponseDto uploadedComm = new MyCommResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCommentsCount(), comm.getCreatedAt());
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
    public ResponseEntity<?> getComms(Principal principal) throws IOException {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Community> commList = communityRepository.findAllByUserOrderByCreatedAtDesc(user);

        //현재까지 작성한 커뮤니티의 정보가 저장될 dto 리스트
        List<MyCommResponseDto> uploadedComms = new ArrayList<>();

        if(!commList.isEmpty()) {
            for (Community comm : commList) {
                MyCommResponseDto uploadedComm = new MyCommResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCommentsCount(), comm.getCreatedAt());
                uploadedComms.add(uploadedComm);
            }
        }
        return new ResponseEntity<>(uploadedComms, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getShortForms(Principal principal) throws IOException {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<ShortForm> sfList = shortFormRepository.findAllByUserOrderByCreatedAtDesc(user);

        //현재까지 작성한 숏폼의 정보가 저장될 dto 리스트
        List<MyImageResponseDto> uploadedSFs = new ArrayList<>();

        if(!sfList.isEmpty()) {
            for (ShortForm sf : sfList) {
                MyImageResponseDto uploadedSF = new MyImageResponseDto(sf.getId(), sf.getImgUrl(), sf.getCreatedAt());
                uploadedSFs.add(uploadedSF);
            }
        }

        return new ResponseEntity<>(uploadedSFs, HttpStatus.OK);
    }

    // get comments - outfit, shortform, community
    @Transactional
    public ResponseEntity<?> getComments(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        //load 3 comments - outfit, community, shortform, reply
        List<CommentOutfit> cmtOutfitList = cmtOutfitRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        List<CommentShortForm> cmtSFList = cmtSFRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        List<CommentCommunity> cmtCommList = cmtCommRepository.findTop3DistinctCommentsByUserOrderByCreatedAtDesc(user, PageRequest.of(0,3));

        List<MyImageResponseDto> cmtOutfits = new ArrayList<>();
        List<MyImageResponseDto> cmtShortForms = new ArrayList<>();
        List<MyCommResponseDto> cmtComms = new ArrayList<>();
        MyCommentsDto myComments = new MyCommentsDto();


        if(!cmtOutfitList.isEmpty()){
            for(CommentOutfit cmt: cmtOutfitList){
                Outfit outfit = cmt.getOutfit();
                MyImageResponseDto cmtOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), cmt.getCreatedAt());
                cmtOutfits.add(cmtOutfit);
            }
        }
        if(!cmtSFList.isEmpty()){
            for(CommentShortForm cmt: cmtSFList){
                ShortForm shortForm = cmt.getShortForm();
                MyImageResponseDto cmtShortForm = new MyImageResponseDto(shortForm.getId(), shortForm.getImgUrl(), cmt.getCreatedAt());
                cmtShortForms.add(cmtShortForm);
            }
        }
        if(!cmtCommList.isEmpty()){
            for(CommentCommunity cmt: cmtCommList){
                Community comm = cmt.getCommunity();
                MyCommResponseDto cmtComm = new MyCommResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCommentsCount(), cmt.getCreatedAt());
                cmtComms.add(cmtComm);
            }
        }
        myComments.setCmtOutfits(cmtOutfits);
        myComments.setCmtShortforms(cmtShortForms);
        myComments.setCmtComms(cmtComms);
        return new ResponseEntity<>(myComments, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getCmtOutfits(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        //load comments - outfit
        List<CommentOutfit> cmtOutfitList = cmtOutfitRepository.findAllByUserOrderByCreatedAtDesc(user);

        List<MyImageResponseDto> cmtOutfits = new ArrayList<>();

        if(!cmtOutfitList.isEmpty()){
            for(CommentOutfit cmt: cmtOutfitList){
                Outfit outfit = cmt.getOutfit();
                MyImageResponseDto cmtOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), cmt.getCreatedAt());
                cmtOutfits.add(cmtOutfit);
            }
        }

        return new ResponseEntity<>(cmtOutfits, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getCmtSFs(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        //load comments - shortform
        List<CommentShortForm> cmtSFList = cmtSFRepository.findAllByUserOrderByCreatedAtDesc(user);

        List<MyImageResponseDto> cmtShortForms = new ArrayList<>();
        if(!cmtSFList.isEmpty()){
            for(CommentShortForm cmt: cmtSFList){
                ShortForm shortForm = cmt.getShortForm();
                MyImageResponseDto cmtShortForm = new MyImageResponseDto(shortForm.getId(), shortForm.getImgUrl(), cmt.getCreatedAt());
                cmtShortForms.add(cmtShortForm);
            }
        }

        return new ResponseEntity<>(cmtShortForms, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getCmtComms(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        //load comments - community
        List<CommentCommunity> cmtCommList = cmtCommRepository.findDistinctCommentsByUserOrderByCreatedAtDesc(user);

        List<MyCommResponseDto> cmtComms = new ArrayList<>();

        if(!cmtCommList.isEmpty()){
            for(CommentCommunity cmt: cmtCommList){
                Community comm = cmt.getCommunity();
                MyCommResponseDto cmtComm = new MyCommResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCommentsCount(), cmt.getCreatedAt());
                cmtComms.add(cmtComm);
            }
        }
        return new ResponseEntity<>(cmtComms, HttpStatus.OK);
    }



    //outfit, product
    @Transactional
    public ResponseEntity<?> getScraps(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Scrap> outfitList = scrapRepository.findTop3ByUserAndOutfitNotNullOrderByCreatedAtDesc(user);
        List<Scrap> productList = scrapRepository.findTop3ByUserAndProductNotNullOrderByCreatedAtDesc(user);

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
    public ResponseEntity<?> getSrpOutfits(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Scrap> outfitList = scrapRepository.findAllByUserAndOutfitNotNullOrderByCreatedAtDesc(user);

        List<MyImageResponseDto> scrapedOutfits = new ArrayList<>();

        if(!outfitList.isEmpty()){
            for(Scrap scrap: outfitList){
                Outfit outfit = scrap.getOutfit();
                MyImageResponseDto scrapedOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), scrap.getCreatedAt());
                scrapedOutfits.add(scrapedOutfit);
            }
        }
        return new ResponseEntity<>(scrapedOutfits, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<?> getSrpProducts(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Scrap> productList = scrapRepository.findAllByUserAndProductNotNullOrderByCreatedAtDesc(user);

        List<MyImageResponseDto> scrapedProducts = new ArrayList<>();

        if(!productList.isEmpty()){
            for(Scrap scrap: productList){
                Product product = scrap.getProduct();
                MyImageResponseDto scrapedProduct = new MyImageResponseDto(product.getId(), product.getImgUrl(), scrap.getCreatedAt());
                scrapedProducts.add(scrapedProduct);
            }
        }
        return new ResponseEntity<>(scrapedProducts, HttpStatus.OK);
    }

    //outfit community shortform
    @Transactional
    public ResponseEntity<?> getLikes(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Heart> outfitList = heartRepository.findTop3ByUserAndOutfitNotNullOrderByCreatedAtDesc(user);
        List<Heart> shortFormList = heartRepository.findTop3ByUserAndShortFormNotNullOrderByCreatedAtDesc(user);

        List<MyImageResponseDto> likedOutfits = new ArrayList<>();
        List<MyImageResponseDto> likedShortForms = new ArrayList<>();
        MyHeartsDto likeHearts = new MyHeartsDto();

        if(!outfitList.isEmpty()){
            for(Heart heart: outfitList){
                Outfit outfit = heart.getOutfit();
                MyImageResponseDto likedOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), heart.getCreatedAt());
                likedOutfits.add(likedOutfit);
            }
        }
        if(!shortFormList.isEmpty()) {
            for (Heart heart : shortFormList) {
                ShortForm shortForm = heart.getShortForm();
                MyImageResponseDto likedShortForm = new MyImageResponseDto(shortForm.getId(), shortForm.getImgUrl(), heart.getCreatedAt());
                likedShortForms.add(likedShortForm);
            }
        }
        likeHearts.setLikedOutfits(likedOutfits);
        likeHearts.setLikedShortForms(likedShortForms);
        return new ResponseEntity<>(likeHearts, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> getLikeOutfits(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Heart> outfitList = heartRepository.findAllByUserAndOutfitNotNullOrderByCreatedAtDesc(user);

        List<MyImageResponseDto> likedOutfits = new ArrayList<>();

        if(!outfitList.isEmpty()){
            for(Heart heart: outfitList){
                Outfit outfit = heart.getOutfit();
                MyImageResponseDto likedOutfit = new MyImageResponseDto(outfit.getId(), outfit.getImgUrl(), heart.getCreatedAt());
                likedOutfits.add(likedOutfit);
            }
        }
        return new ResponseEntity<>(likedOutfits, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getLikeSFs(Principal principal) throws IOException{
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        List<Heart> shortFormList = heartRepository.findAllByUserAndShortFormNotNullOrderByCreatedAtDesc(user);

        List<MyImageResponseDto> likedShortForms = new ArrayList<>();


        if(!shortFormList.isEmpty()){
            for(Heart heart: shortFormList){
                ShortForm shortForm = heart.getShortForm();
                MyImageResponseDto likedShortForm = new MyImageResponseDto(shortForm.getId(), shortForm.getImgUrl(), heart.getCreatedAt());
                likedShortForms.add(likedShortForm);
            }
        }
        return new ResponseEntity<>(likedShortForms, HttpStatus.OK);
    }
}




