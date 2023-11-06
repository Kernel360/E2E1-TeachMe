package kr.kernel.teachme.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

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
	private Long lectureId;

	public MemberFavor(
		Long memberId,
		Long lectureId
	) {
		this.memberId = memberId;
		this.lectureId = lectureId;
	}
}
