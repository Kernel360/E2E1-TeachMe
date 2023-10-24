package kr.kernel360.teachme.review.entity;

import kr.kernel360.teachme.lecture.entity.Lecture;
import kr.kernel360.teachme.member.entity.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@Table(name = "review")
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
    public Review(Long id, Member member, Lecture lecture, double score, String content) {
        this.id = id;
        this.member = member;
        this.lecture = lecture;
        this.score = score;
        this.content = content;
    }
}
