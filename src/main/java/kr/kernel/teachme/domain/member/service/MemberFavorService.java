package kr.kernel.teachme.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.kernel.teachme.common.jwt.JwtUtils;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.entity.MemberFavor;
import kr.kernel.teachme.domain.member.repository.MemberFavorRepository;
import kr.kernel.teachme.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFavorService {
	private final MemberFavorRepository memberFavorRepository;
	private final MemberRepository memberRepository;
	private final LectureRepository lectureRepository;
	JwtUtils jwtUtils;
	public List<Lecture> getFavorLectureList(String token) {
		String userName = jwtUtils.getUsername(token);
		Member member = memberRepository.findByUsername(userName);
		List<MemberFavor> favors = memberFavorRepository.findAllByMemberId(member.getId());
		List<Lecture> favoritedLectures = favors.stream()
			.map(MemberFavor::getLecture)
			.collect(Collectors.toList());
		return favoritedLectures;
	}
	public void addFavorLecture(String token, Long lectureId) {
		String userName = jwtUtils.getUsername(token);
		Member member = memberRepository.findByUsername(userName);
		Optional<Lecture> lectureInfo = lectureRepository.findById(lectureId);
		Lecture lecture = lectureInfo.get();
		MemberFavor favorLecture = MemberFavor.builder()
			.memberId(member.getId())
			.lecture(lecture)
			.build();
		memberFavorRepository.save(favorLecture);
	}

	public void deleteFavorLecture(String token, Long lectureId) {
		String userName = jwtUtils.getUsername(token);
		Member member = memberRepository.findByUsername(userName);
		MemberFavor deleteLecture = memberFavorRepository.findByMemberIdAndLectureId(member.getId(), lectureId);
		memberFavorRepository.delete(deleteLecture);
	}
}
