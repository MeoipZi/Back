package meoipzi.meoipzi.outfit.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.genre.domain.Genre;
import meoipzi.meoipzi.genre.repository.GenreRepository;
import meoipzi.meoipzi.genreoutfit.domain.GenreOutfit;
import meoipzi.meoipzi.genreoutfit.repository.GenreOutfitRepository;
import meoipzi.meoipzi.heart.repository.HeartRepository;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.outfit.dto.*;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.product.dto.ProductListResponseDTO;
import meoipzi.meoipzi.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final GenreOutfitRepository genreOutfitRepository;
    private final S3Config s3Config;

    private final ProductService productService;
    private final HeartRepository heartRepository;

    //코디 등록
    @Transactional
    public ResponseEntity<?> saveOutfit(OutfitRequestDTO outfitRequestDTO) throws Exception{
        User user = userRepository.findByUsername(outfitRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + outfitRequestDTO.getUsername()));//status code 500

        try{
            if(outfitRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(outfitRequestDTO.getImgUrl());
                Outfit outfit = outfitRequestDTO.toEntity(user);
                for (Long genreId : outfitRequestDTO.getGenreIds()) {
                    Genre genre = genreRepository.findById(genreId)
                            .orElseThrow(() -> new Exception("Could not find genre with ID: " + genreId));
                    GenreOutfit genreOutfit = new GenreOutfit(genre, outfit);
                    genreOutfitRepository.save(genreOutfit);// 장르와 아웃핏 연결 저장
                    outfit.getGenreOutfits().add(genreOutfit);
                }
                outfit.setImgUrl(filePath);
                outfitRepository.save(outfit);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(outfitRequestDTO);
    }

    @Transactional
    public  ResponseEntity<?>  updateOutfit(Long outfitId, OutfitUpdateRequestDTO outfitUpdateRequestDTO) {
        User user = userRepository.findByUsername(outfitUpdateRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + outfitUpdateRequestDTO.getUsername()));

        try {
            Outfit existingOutfit = outfitRepository.findById(outfitId)
                    .orElseThrow(() -> new RuntimeException("해당 ID의 제품을 찾을 수 없습니다."));

            if (outfitUpdateRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(outfitUpdateRequestDTO.getImgUrl());
                existingOutfit.setImgUrl(filePath);
            }
            existingOutfit.setContent(outfitUpdateRequestDTO.getContent());
            outfitRepository.save(existingOutfit);
        } catch (IOException e) {
                throw new RuntimeException("Failed to update outfit: " + e.getMessage(), e);
            }
        return ResponseEntity.ok(outfitUpdateRequestDTO);
    }

    @Transactional
    public void deleteOutfit(Long outfitId){
        outfitRepository.deleteById(outfitId);
    }

    public OutfitResponseDTO clickOutfit(Long outfitId, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + username));

        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 제품을 찾을 수 없습니다."));
        List<ProductListResponseDTO> productListResponseDTOS = productService.getProductsByOutfitId(outfitId);

        OutfitResponseDTO outfitResponseDTO = new OutfitResponseDTO(outfit);
        outfitResponseDTO.setProducts(productListResponseDTOS);
        if(heartRepository.findByUserAndOutfit(user, outfit).isPresent())
            outfitResponseDTO.setLikeOrNot(true);

        return outfitResponseDTO;
    }


    public Page<OutfitTotalResponseDTO> getLatestOutfits(Pageable pageable) {
        try {
            Page<Outfit> outfitsPage = outfitRepository.findAllByOrderByIdDesc(pageable);
            List<OutfitTotalResponseDTO> outfitDTOs = outfitsPage.stream()
                    .map(OutfitTotalResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(outfitDTOs, pageable, outfitDTOs.size());
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리 또는 로깅을 통한 추가 조사
            return new PageImpl<>(Collections.emptyList());
        }
    }

    public Page<OutfitTotalResponseDTO> getPopularOutfits(Pageable pageable){
        try {
            Page<Outfit> outfitsPage= outfitRepository.findByOrderByLikesCountDesc(pageable);
            List<OutfitTotalResponseDTO> outfitDTOs = outfitsPage.stream()
                    .map(OutfitTotalResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(outfitDTOs, pageable, outfitsPage.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리 또는 로깅을 통한 추가 조사
            return new PageImpl<>(Collections.emptyList());
        }
    }

    public Page<OutfitsGenreResponseDTO> getLatestOutfitsByGenre(Long genreId, Pageable pageable){
        try{
            Genre genre = genreRepository.findGenreById(genreId);
            if (genre == null) {
                return new PageImpl<>(Collections.emptyList());
            }
            List<GenreOutfit> genreOutfits = genre.getGenreOutfits();
            List<OutfitsGenreResponseDTO> outfitsDTOList = genreOutfits.stream()
                    .map(genreOutfit -> {
                        OutfitsGenreResponseDTO responseDTO = new OutfitsGenreResponseDTO();
                        Outfit outfit = genreOutfit.getOutfit();
                        responseDTO.setOutfitId(outfit.getId());
                        responseDTO.setImgUrl(outfit.getImgUrl());
                        return responseDTO;
                    })
                    .collect(Collectors.toList());
            return new PageImpl<>(outfitsDTOList, pageable, outfitsDTOList.size());
        }catch (Exception e){
            e.printStackTrace();
            return new PageImpl<>(Collections.emptyList());
        }
    }


}