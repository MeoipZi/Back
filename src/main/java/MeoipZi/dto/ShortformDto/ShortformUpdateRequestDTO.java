package MeoipZi.dto.ShortformDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortformUpdateRequestDTO {
    private String userName;

    private String title;
    private String contents;
    private MultipartFile imgUrl;
}
