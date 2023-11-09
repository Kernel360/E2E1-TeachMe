package kr.kernel.teachme.domain.review.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.review.entity.Review;
import kr.kernel.teachme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final LectureRepository lectureRepository;
	private final ReviewRepository reviewRepository;

	public Page<Review> getLectureReviewList(Pageable pageable,Long lectureId) {
		return reviewRepository.findByLectureId(lectureId, pageable);
	}

	public Page<Review> getMemberReviewList(Pageable pageable, Member member) {
		return reviewRepository.findByMemberId(member.getId(), pageable);
	}

	public void addLectureReview(Member member, Long lectureId, String content, double score) {
		Optional<Lecture> lectureInfo = lectureRepository.findById(lectureId);
		Lecture lecture = lectureInfo.get();
		Date date = new Date();
		Review lectureReview = Review.builder()
			.member(member)
			.lecture(lecture)
			.score(score)
			.content(content)
			.createDate(date)
			.updateDate(date)
			.build();
		reviewRepository.save(lectureReview);
	}
	public void updateLectureReview(Member member, Long lectureId, String content, double score) {
		Review reviewInfo = reviewRepository.findByLectureIdAndMemberId(member.getId(), lectureId);
		Optional<Lecture> lectureInfo = lectureRepository.findById(lectureId);
		Lecture lecture = lectureInfo.get();
		Date date = new Date();
		Review lectureReview = Review.builder()
			.member(member)
			.lecture(lecture)
			.score(score)
			.content(content)
			.createDate(reviewInfo.getCreateDate())
			.updateDate(date)
			.build();
		reviewRepository.save(lectureReview);

	}

	public void deleteLectureReview(Member member, Long lectureId) {
		Review deleteReview = reviewRepository.findByLectureIdAndMemberId(lectureId, member.getId());
		reviewRepository.delete(deleteReview);
	}
}
