package MeoipZi.meoipzi.dto.CommunityDto;


import MeoipZi.meoipzi.domain.Community;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/* 커뮤니티 글 목록 조회 시 반환할 DTO */
public class CommunityListResponseDTO {
    private Long communityId;
    private String title; // 제목
    private String imgUrl; // 첨부 이미지 파일
    private LocalDateTime createdAt; // 글 작성 일자
    private Long cmt_count; // 댓글 갯수
    private Long heart_count; // 하트 개수
    private String formattedCreatedAt; // 'n일 전' 형식
    private String category; // 카테고리

    public CommunityListResponseDTO(Community community){
        this.communityId = community.getId();
        this.title = community.getTitle();
        this.imgUrl = community.getImgUrl();
        this.createdAt = community.getCreatedAt();
        this.formattedCreatedAt = calculateTimeAgo(createdAt);
        // this.cmt_count = ; // this.heart_count = ;
        this.category = community.getCategory();
    }

    // 문자열 계산 메서드
    private String calculateTimeAgo(LocalDateTime createdAt){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt ,now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();
        long weeks = days / 7;
        long months = days / 30;

        if(minutes < 1) {
            return "방금 전";
        } else if(hours < 1) {
            return minutes + "분 전";
        } else if (days < 1) {
            return hours + "시간 전";
        } else if(days < 7) {
            return days + "일 전";
        } else if (weeks < 4) {
            return weeks + "주 전";
        } else {
            return months + "개월 전";
        }
    }


    // 정렬된 게시글 목록을 반환하는 메서드
    public static List<CommunityResponseDTO> sortByCategory(List<Community> communities, String targetCategory) {
        return communities.stream()
                .filter(community -> community.getCategory().equals(targetCategory))
                .map(CommunityResponseDTO::new)
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
