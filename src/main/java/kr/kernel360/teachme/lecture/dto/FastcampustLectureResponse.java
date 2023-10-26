package kr.kernel360.teachme.lecture.dto;

import kr.kernel360.teachme.lecture.entity.FastcampusLecture;
import kr.kernel360.teachme.lecture.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public FastcampusLecture toEntity(){
        return FastcampusLecture.builder()
                .uniqueId(id)
                .state(state)
                .slug(slug)
                .publicTitle(publicTitle)
                .publicDescription(publicDescription)
                .keywords(keywords)
                .desktopCardAsset(desktopCardAsset)
                .build();
    }

    public Lecture toLectureEntity(){
        return Lecture.builder()
            .lectureId(id)
            .platform("fastcampus")
            .title(publicTitle)
            .description(publicDescription)
            .keywords(keywords)
            .url("https://fastcampus.co.kr/" + slug)
            .img(desktopCardAsset)
            .build();
    }
}


