package kr.kernel360.teachme.lecture.dto;

import kr.kernel360.teachme.lecture.entity.Fastcampus;
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

    public Fastcampus toEntity(){
        return Fastcampus.builder()
                .id(this.id)
                .state(this.state)
                .slug(this.slug)
                .publicTitle(this.publicTitle)
                .publicDescription(this.publicDescription)
                .keywords(this.keywords)
                .desktopCardAsset(this.desktopCardAsset);
    }
}


