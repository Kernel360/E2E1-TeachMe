package kr.kernel.teachme.crawler.dto;

import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.crawler.entity.FastcampusLecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastcampusLectureResponse {

    private Lecture id;

    private String state;

    private String slug;

    private String publicTitle;

    private String publicDescription;

    private String keywords;

    private String desktopCardAsset;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public FastcampusLecture toEntity(){
        return FastcampusLecture.builder()
                .uniqueId(id)
                .state(state)
                .slug(slug)
                .publicTitle(publicTitle)
                .publicDescription(publicDescription)
                .keywords(keywords)
                .desktopCardAsset(desktopCardAsset)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    // public Lecture toLectureEntity(){
    //     return Lecture.builder()
    //         .lectureId(id)
    //         .platform("fastcampus")
    //         .title(publicTitle)
    //         .description(publicDescription)
    //         .keywords(keywords)
    //         .url("https://fastcampus.co.kr/" + slug)
    //         .img(desktopCardAsset)
    //         .build();
    // }
}


