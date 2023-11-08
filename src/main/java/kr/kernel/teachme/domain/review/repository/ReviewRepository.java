package kr.kernel.teachme.domain.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.kernel.teachme.domain.review.entity.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findByLectureId(Long lectureId);
	Review findByLectureIdAndMemberId(Long lectureId, Long memberId);
}
