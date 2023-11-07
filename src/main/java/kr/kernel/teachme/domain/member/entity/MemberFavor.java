package kr.kernel.teachme.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class MemberFavor {
	@GeneratedValue
	@Id
	private Long id;


	private Long memberId;

	@JoinColumn (name ="lecture_id")
	@ManyToOne
	private Lecture lecture;

	@Builder
	protected MemberFavor(Long memberId, Lecture lecture
	) {
		this.memberId = memberId;
		this.lecture = lecture;
	}
}
