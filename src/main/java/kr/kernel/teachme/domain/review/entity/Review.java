package kr.kernel.teachme.domain.review.entity;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

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
