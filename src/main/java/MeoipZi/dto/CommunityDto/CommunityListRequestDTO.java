package MeoipZi.dto.CommunityDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// 사용자 조회 시 카테고리 값을 클라이언트가 담아 넣어 요청한다.
@Getter
@Setter
@RequiredArgsConstructor
public class CommunityListRequestDTO {
    private String category; // 카테고리
}
