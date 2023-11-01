package kr.kernel.teachme.lecture.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "lecture")
@Getter
@Setter
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

    private String instructor;

    private int price;

    private int discountPrice;

    private int duration;

    private Date createDate;

    private Date updateDate;

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