package kr.kernel.teachme.domain.lecture.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

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

    private String instructor;

    private int price;

    private int discountPrice;

    private int duration;

    private Date createDate;

    private Date updateDate;

    @ColumnDefault("false")
    private boolean detailUploadFlag;

    @Builder
    protected Lecture(Long lectureId, String platform, String title, String description, String keywords, String url, String img, int price, int discountPrice, String instructor, Date createDate, Date updateDate){
        this.platform = platform;
        this.lectureId = lectureId;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.url = url;
        this.img = img;
        this.price = price;
        this.discountPrice = discountPrice;
        this.instructor = instructor;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public void updateInflearnDetailInfo(int duration, String img, Date createDate, Date updateDate) {
        this.duration = duration;
        this.img = img;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.detailUploadFlag = true;
    }

    public void updateFastcampusDetailInfo(int price, int discountPrice, String instructor, int duration) {
        this.price = price;
        this.discountPrice = discountPrice;
        this.instructor = instructor;
        this.duration = duration;
        this.detailUploadFlag = true;
    }

}