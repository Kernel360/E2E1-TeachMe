package kr.kernel.teachme.domain.member.service;

import java.util.List;
import org.springframework.stereotype.Service;

import kr.kernel.teachme.domain.member.entity.MemberFavor;
import kr.kernel.teachme.domain.member.repository.MemberFavorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFavorService {
	private final MemberFavorRepository memberFavorRepository;

	public List<MemberFavor> getFavorLectureList(Long memberId) {
		return memberFavorRepository.findAllByMemberId(memberId);
	}
	public MemberFavor addFavorLecture(Long lectureId, Long memberId) {
		MemberFavor memberFavor = memberFavorRepository.findByMemberIdAndLectureId(memberId, lectureId);
		return memberFavorRepository.save(memberFavor);
	}

	public void deleteFavorLecture(Long lectureId, Long memberId) {
		MemberFavor memberFavor = memberFavorRepository.findByMemberIdAndLectureId(memberId, lectureId);
		memberFavorRepository.delete(memberFavor);
	}
}
