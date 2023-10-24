package kr.kernel360.teachme.review.entity;

import kr.kernel360.teachme.lecture.entity.Lecture;
import kr.kernel360.teachme.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name = "review")
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    private double score;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    protected Review(Member member, Lecture lecture, double score, String content) {
        Assert.notNull(member, "Review member must not be null");
        this.member = member;
        Assert.notNull(lecture, "Review lecture must not be null");
        this.lecture = lecture;
        this.score = score;
        Assert.hasLength(content, "Review content must not be empty");
        this.content = content;
    }
}
