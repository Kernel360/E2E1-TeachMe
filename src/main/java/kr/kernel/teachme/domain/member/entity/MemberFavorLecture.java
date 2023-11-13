package kr.kernel.teachme.domain.member.entity;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table
@Getter
@NoArgsConstructor
public class MemberFavorLecture {
	@GeneratedValue
	@Id
	private Long id;


	private Long memberId;

	@JoinColumn (name ="lecture_id")
	@ManyToOne
	private Lecture lecture;

	@Builder
	protected MemberFavorLecture(Long memberId, Lecture lecture
	) {
		this.memberId = memberId;
		this.lecture = lecture;
	}
}
