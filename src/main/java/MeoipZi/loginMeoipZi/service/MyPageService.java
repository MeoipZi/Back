package MeoipZi.loginMeoipZi.service;

import MeoipZi.loginMeoipZi.domain.Community;
import MeoipZi.loginMeoipZi.domain.ShortForm;
import MeoipZi.loginMeoipZi.domain.User;
import MeoipZi.loginMeoipZi.dto.CommunityResponseDto;
import MeoipZi.loginMeoipZi.dto.MultipleDto;
import MeoipZi.loginMeoipZi.dto.ShortFormResponseDto;
import MeoipZi.loginMeoipZi.exception.NotFoundContentException;
import MeoipZi.loginMeoipZi.exception.NotFoundMemberException;
import MeoipZi.loginMeoipZi.repository.CommunityRepository;
import MeoipZi.loginMeoipZi.repository.ShortFormRepository;
import MeoipZi.loginMeoipZi.repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<?> uploadedFeeds(Principal principal) throws IOException {

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
        List<CommunityResponseDto> uploadedComms = new ArrayList<>();
        List<ShortFormResponseDto> uploadedSFs = new ArrayList<>();
        MultipleDto uploadedFeeds = new MultipleDto();

        if(!commList.isEmpty()) {
            for (Community comm : commList) {
                CommunityResponseDto uploadedComm = new CommunityResponseDto(comm.getId(), comm.getTitle(), comm.getLikesCount(), comm.getCmtCount(), comm.getCreatedAt());
                uploadedComms.add(uploadedComm);
            }
        }
        if(!sfList.isEmpty()) {
            for (ShortForm sf : sfList) {
                ShortFormResponseDto uploadedSF = new ShortFormResponseDto(sf.getId(), sf.getTitle(), sf.getImgUrl(), sf.getCreatedAt());
                uploadedSFs.add(uploadedSF);
            }
        }

        uploadedFeeds.setUploadedComms(uploadedComms);
        uploadedFeeds.setUploadedSFs(uploadedSFs);
        return new ResponseEntity<>(uploadedFeeds, HttpStatus.OK);
    }
}
