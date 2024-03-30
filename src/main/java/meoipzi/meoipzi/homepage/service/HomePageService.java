package meoipzi.meoipzi.homepage.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.homepage.domain.Partners;
import meoipzi.meoipzi.homepage.domain.VintageNews;
import meoipzi.meoipzi.homepage.dto.*;
import meoipzi.meoipzi.homepage.repository.PartnersRepository;
import meoipzi.meoipzi.homepage.repository.VintageNewsRepository;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.outfit.dto.OutfitResponseDTO;
import meoipzi.meoipzi.outfit.dto.OutfitTotalResponseDTO;
import meoipzi.meoipzi.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.outfit.service.OutfitService;
import meoipzi.meoipzi.product.domain.Product;
import meoipzi.meoipzi.product.dto.ProductListResponseDTO;
import meoipzi.meoipzi.product.dto.ProductRequestDTO;
import meoipzi.meoipzi.product.dto.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomePageService {
    private final UserRepository userRepository;
    private final S3Config s3Config;
    private final VintageNewsRepository vintageNewsRepository;
    private final PartnersRepository partnersRepository;
    private final OutfitRepository outfitRepository;

    @Autowired
    private OutfitService outfitService;
    // 관리자 -> 빈티지 소식 등록하기
    @Transactional
    public ResponseEntity<?> saveVintage(VintageNewsRequestDTO vintageNewsRequestDTO){
        User user = userRepository.findByUsername(vintageNewsRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + vintageNewsRequestDTO.getUsername()));

        try{
            if(vintageNewsRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(vintageNewsRequestDTO.getImgUrl());
                VintageNews vintageNews = vintageNewsRequestDTO.toEntity(user); //DTO를 엔티티로 변환
                vintageNews.setImgUrl(filePath);
                vintageNewsRepository.save(vintageNews);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(vintageNewsRequestDTO);
    }

    // 관리자 -> 제휴기업 정보 등록하기
    @Transactional
    public ResponseEntity<?> savePartners(PartnersRequestDTO partnersRequestDTO){
        User user = userRepository.findByUsername(partnersRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + partnersRequestDTO.getUsername()));

        try{
            if(partnersRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(partnersRequestDTO.getImgUrl());
                Partners partners = partnersRequestDTO.toEntity(user); //DTO를 엔티티로 변환
                partners.setImgUrl(filePath);
                partners.setShopName(partnersRequestDTO.getShopName());
                partners.setShopUrl(partners.getShopUrl());
                partnersRepository.save(partners);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(partnersRequestDTO);
    }

    // 관리자 -> 빈티지 소식 삭제하기
    @Transactional
    public void deleteVintage(Long vintageNewsId){
        vintageNewsRepository.deleteById(vintageNewsId);
    }

    // 관리자 -> 제휴기업 정보 삭제하기
    @Transactional
    public void deletePartners(Long partnersId){
        partnersRepository.deleteById(partnersId);
    }

    // 홈페이지 메인 화면
    @Transactional
    // 로그인 여부와 상관없이, 홈페이지는 다 볼 수 있다.
    public HomePageDTO getHomepageInfo() {
        List<VintageNews> vintageNewsList = vintageNewsRepository.findAllByOrderByCreatedAtDesc();
        List<Partners> partnersList = partnersRepository.findAll();
        List<Outfit> outfitList = outfitRepository.findAll();

        // VintageNews를 VintageNewsResponseDTO로 변환
        List<VintageNewsResponseDTO> vintageNewsResponseDTOList = vintageNewsList.stream()
                .map(vintageNews -> {
                    VintageNewsResponseDTO dto = new VintageNewsResponseDTO();
                    dto.setImgUrl(vintageNews.getImgUrl());
                    return dto;
                })
                .collect(Collectors.toList());

        // Partners를 PartnersResponseDTO로 변환
        List<PartnersResponseDTO> partnersResponseDTOList = partnersList.stream()
                .map(partner -> {
                    PartnersResponseDTO dto = new PartnersResponseDTO();
                    dto.setImgUrl(partner.getImgUrl());
                    dto.setShopUrl(partner.getShopUrl());
                    return dto;
                })
                .collect(Collectors.toList());

        Page<OutfitTotalResponseDTO> styleList = outfitService.getPopularOutfits(PageRequest.of(0, 10));

        // HomePageDTO에 데이터 채우기
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setVintageNewsList(vintageNewsResponseDTOList);
        homePageDTO.setPartnersList(partnersResponseDTOList);
        homePageDTO.setStyleList(styleList);
        return homePageDTO;
    }

    // 빈티지 소식 상세 조회
    @Transactional
    public VintageNewsResponseDTO clickVintage(Long vintageNewsId){
        VintageNews vintageNews = vintageNewsRepository.findById(vintageNewsId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 빈티지 소식을 찾을 수 없습니다."));

        return new VintageNewsResponseDTO(vintageNews);
    }

    // 제휴기업 연결 링크
    @Transactional
    public PartnersDetailResponseDTO clickPartners(Long partnersId){
        Partners partners = partnersRepository.findById(partnersId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 제휴기업 링크에 연결할 수 없습니다."));

        return new PartnersDetailResponseDTO(partners);
    }

}