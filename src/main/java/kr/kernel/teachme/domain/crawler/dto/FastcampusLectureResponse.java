package kr.kernel.teachme.domain.crawler.dto;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static kr.kernel.teachme.common.util.DateUtil.convertLocalDateTimeToDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastcampusLectureResponse {

    private Long id;

    private String state;

    private String slug;

    private String publicTitle;

    private String publicDescription;

    private String keywords;

    private String desktopCardAsset;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Lecture toEntity(){
        return Lecture.builder()
                .lectureId(id)
                .platform("fastcampus")
                .title(publicTitle)
                .description(publicDescription)
                .keywords(trimFirstAndLastCharacter(keywords))
                .img(desktopCardAsset)
                .url(convertSlugToUrl(slug))
                .createDate(convertLocalDateTimeToDate(createdAt))
                .updateDate(convertLocalDateTimeToDate(updatedAt))
                .build();
    }


    private String trimFirstAndLastCharacter(String keywords) {
        if(keywords.length() <= 2) {
            return keywords;
        }

        return keywords.substring(1, keywords.length() - 1);
    }

    private String convertSlugToUrl(String slug) {
        return "https://fastcampus.co.kr/" + slug;
    }

}


