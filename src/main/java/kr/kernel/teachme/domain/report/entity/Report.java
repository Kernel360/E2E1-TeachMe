package kr.kernel.teachme.domain.report.entity;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Table(name = "report")
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    private int reviewCount;

    private double averageScore;

    @Builder
    protected Report(LocalDate date, Lecture lecture, int reviewCount, double averageScore) {
        this.date = date;
        this.lecture = lecture;
        this.reviewCount = reviewCount;
        this.averageScore = averageScore;
    }
}
