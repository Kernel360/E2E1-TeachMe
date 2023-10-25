package kr.kernel360.teachme.lecture.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FastcampustLectureResponse {

    private Long id;

    private String state;

    private String slug;

    private String publicTitle;

    private String publicDescription;

    private String keywords;

    private String desktopCardAsset;
}
