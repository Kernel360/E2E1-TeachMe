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

    private String uniqueId;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;

    private String teacher;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private BigDecimal score;

    @Builder
    protected Lecture(String uniqueId, Platform platform, String teacher, String url, String imageUrl, Category category, BigDecimal score) {
        Assert.hasLength(uniqueId, "Lecture unique id must not be empty");
        this.uniqueId = uniqueId;
        Assert.notNull(platform, "Lecture platform must not be null");
        this.platform = platform;
        this.teacher = teacher;
        this.url = url;
        this.imageUrl = imageUrl;
        this.category = category;
        this.score = score;
    }
}
