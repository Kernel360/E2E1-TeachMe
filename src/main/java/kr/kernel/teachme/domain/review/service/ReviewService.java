package kr.kernel.teachme.domain.review.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.kernel.teachme.common.jwt.JwtUtils;
import kr.kernel.teachme.common.util.DateUtil;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.repository.MemberRepository;
import kr.kernel.teachme.domain.review.entity.Review;
import kr.kernel.teachme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final MemberRepository memberRepository;
	private final LectureRepository lectureRepository;
	private final ReviewRepository reviewRepository;

	JwtUtils jwtUtils;

	public List<Review> getLectureReviewList(Long lectureId) {
		return reviewRepository.findByLectureId(lectureId);
	}

	public void addLectureReiview(String token, Long lectureId, String content, double score) {
		String userName = jwtUtils.getUsername(token);
		Member member = memberRepository.findByUsername(userName);
		Optional<Lecture> lectureInfo = lectureRepository.findById(lectureId);
		Lecture lecture = lectureInfo.get();
		Review lectureReview = Review.builder()
			.member(member)
			.lecture(lecture)
			.score(score)
			.content(content)
			.createDate(DateUtil.convertLocalDateTimeToDate(LocalDateTime.now()))
			.updateDate(DateUtil.convertLocalDateTimeToDate(LocalDateTime.now()))
			.build();
		reviewRepository.save(lectureReview);
	}

	public void deleteLectureReview(String token, Long lectureId) {
		String userName = jwtUtils.getUsername(token);
		Member member = memberRepository.findByUsername(userName);
		Review deleteReview = reviewRepository.findByLectureIdAndMemberId(lectureId, member.getId());
		reviewRepository.delete(deleteReview);
	}
}
