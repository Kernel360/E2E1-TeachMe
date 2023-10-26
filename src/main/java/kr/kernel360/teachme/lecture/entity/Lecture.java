package kr.kernel360.teachme.lecture.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name = "lecture")
@Getter
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;

    private String platform;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String keywords;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String img;

    @Builder
    protected Lecture(Long lectureId, String platform, String title, String description, String keywords, String url, String img){
        this.lectureId = lectureId;
        this.platform = platform;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.url = url;
        this.img = img;
    }

}