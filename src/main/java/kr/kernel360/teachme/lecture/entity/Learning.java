package kr.kernel360.teachme.lecture.entity;

import kr.kernel360.teachme.member.entity.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
    public Learning(Long id, Lecture lecture, Member member) {
        this.id = id;
        this.lecture = lecture;
        this.member = member;
    }

}
