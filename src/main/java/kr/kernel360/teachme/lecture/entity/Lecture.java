package kr.kernel360.teachme.lecture.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    protected Lecture(Long id, Platform platform, String teacher, String url, String imageUrl, Category category, BigDecimal score) {
        this.id = id;
        this.platform = platform;
        this.teacher = teacher;
        this.url = url;
        this.imageUrl = imageUrl;
        this.category = category;
        this.score = score;
    }
}
