package kr.kernel.teachme.domain.review.service;

import kr.kernel.teachme.common.exception.NotOwnerForReviewException;
import kr.kernel.teachme.common.exception.ReviewNotFoundException;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.review.dto.ReviewRequest;
import kr.kernel.teachme.domain.review.entity.Review;
import kr.kernel.teachme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

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

	public void updateLectureReview(Member member, ReviewRequest request) {
		Optional<Review> reviewInfo = reviewRepository.findById(request.getReviewId());
		Review review = reviewInfo.orElseThrow(ReviewNotFoundException::new);
		if(!Objects.equals(review.getMember().getId(), member.getId())) throw new NotOwnerForReviewException();
		review.updateReview(request.getScore(), request.getContent());
		reviewRepository.save(review);
	}

	public void deleteLectureReview(Member member, Long reviewId) {
		Optional<Review> deleteReview = reviewRepository.findById(reviewId);
		Review review = deleteReview.orElseThrow(ReviewNotFoundException::new);
		if(!Objects.equals(review.getMember().getId(), member.getId())) throw new NotOwnerForReviewException();
		reviewRepository.delete(review);
	}
}
