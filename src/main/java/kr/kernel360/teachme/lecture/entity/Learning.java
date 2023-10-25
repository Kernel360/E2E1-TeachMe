package kr.kernel360.teachme.lecture.entity;

import kr.kernel360.teachme.member.entity.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "learning")
public class Learning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    protected Learning(Lecture lecture, Member member) {
        Assert.notNull(lecture, "Learning lecture must not be null");
        this.lecture = lecture;
        Assert.notNull(member, "Learning member must not be null");
        this.member = member;
    }

}
