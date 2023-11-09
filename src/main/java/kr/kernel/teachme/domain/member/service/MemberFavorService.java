package kr.kernel.teachme.domain.member.service;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.entity.MemberFavorLecture;
import kr.kernel.teachme.domain.member.repository.MemberFavorRepository;
import kr.kernel.teachme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberFavorService {
	private final MemberFavorRepository memberFavorRepository;
	private final LectureRepository lectureRepository;
	public Page<Lecture> getFavorLectureList(Member member, Pageable pageable) {
		Page<MemberFavorLecture> favorsPage = memberFavorRepository.findAllByMemberId(member.getId(), pageable);
		Page<Lecture> lecturePage = favorsPage.map(MemberFavorLecture::getLecture);
		return lecturePage;
	}
	public void addFavorLecture(Member member, Long lectureId) {
		Optional<Lecture> lectureInfo = lectureRepository.findById(lectureId);
		Lecture lecture = lectureInfo.orElseThrow(IllegalArgumentException::new);
		MemberFavorLecture favorLecture = MemberFavorLecture.builder()
			.memberId(member.getId())
			.lecture(lecture)
			.build();
		memberFavorRepository.save(favorLecture);
	}

	public void deleteFavorLecture(Member member, Long lectureId) {
		MemberFavorLecture deleteLecture = memberFavorRepository.findByMemberIdAndLectureId(member.getId(), lectureId);
		memberFavorRepository.delete(deleteLecture);
	}

	public boolean isFavorLecture(Member member, Long lectureId) {
		return memberFavorRepository.existsByMemberIdAndLectureId(member.getId(), lectureId);
	}
}
