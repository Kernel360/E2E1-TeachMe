package kr.kernel.teachme.domain.lecture.entity;

import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureUpdateResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

import static kr.kernel.teachme.common.util.DateUtil.convertLocalDateTimeToDate;

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

    private Date lastCrawlDate;

    @ColumnDefault("false")
    private boolean detailUploadFlag;

    @Builder
    public Lecture(Long lectureId, String platform, String title, String description, String keywords, String url, String img, int price, int discountPrice, String instructor, Date createDate, Date updateDate){
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
        this.lastCrawlDate = new Date(1900, Calendar.JANUARY, 1);
    }

    public void updateInflearnDetailInfo(int duration, String img, Date createDate, Date updateDate) {
        this.duration = duration;
        this.img = img;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.detailUploadFlag = true;
        this.lastCrawlDate = new Date();
    }

    public void updateFastcampusDetailInfo(FastcampusLectureUpdateResponse fastcampusLectureUpdateResponse) {
        this.instructor = fastcampusLectureUpdateResponse.getInstructor();
        this.duration = fastcampusLectureUpdateResponse.getTotalClassHours() * 60;
        this.detailUploadFlag = true;
        this.lastCrawlDate = new Date();
        this.createDate = (null == fastcampusLectureUpdateResponse.getCreatedAt()) ? createDate : convertLocalDateTimeToDate(fastcampusLectureUpdateResponse.getCreatedAt());
        this.updateDate = (null == fastcampusLectureUpdateResponse.getUpdatedAt()) ? updateDate : convertLocalDateTimeToDate(fastcampusLectureUpdateResponse.getUpdatedAt());
        this.description = fastcampusLectureUpdateResponse.getPublicDescription();
        this.keywords = fastcampusLectureUpdateResponse.getKeywords();
        this.price = fastcampusLectureUpdateResponse.getPrice();
        this.discountPrice = fastcampusLectureUpdateResponse.getDiscountPrice();
    }

}