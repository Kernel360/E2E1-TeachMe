package kr.kernel360.teachme.lecture.dto;

import kr.kernel360.teachme.lecture.entity.Fastcampus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastcampustLectureResponse {

    private Long id;

    private String state;

    private String slug;

    private String publicTitle;

    private String publicDescription;

    private String keywords;

    private String desktopCardAsset;

    public Fastcampus toEntity(Fastcampus fastcampus){
        return fastcampus.builder()
                .id(id)
                .state(state)
                .slug(slug)
                .publicTitle(publicTitle)
                .publicDescription(publicDescription)
                .keywords(keywords)
                .desktopCardAsset(desktopCardAsset).build();
    }
}


