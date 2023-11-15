package kr.kernel.teachme.domain.review.repository;

import kr.kernel.teachme.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	Page<Review> findByLectureId(Long lectureId, Pageable pageable);

	Page<Review> findByMemberId(Long memberId, Pageable pageable);
	Review findByLectureIdAndMemberId(Long lectureId, Long memberId);
	List<Review> findByCreateDate(Date date);
}
