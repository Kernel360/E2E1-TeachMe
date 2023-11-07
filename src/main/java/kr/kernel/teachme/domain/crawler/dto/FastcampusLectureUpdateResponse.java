package kr.kernel.teachme.domain.crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastcampusLectureUpdateResponse {
    private int price;
    private int discountPrice;
    private String instructor;
    private int totalClassHours;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String publicDescription;
    private String keywords;
}



