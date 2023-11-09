package kr.kernel.teachme.domain.member.repository;

import kr.kernel.teachme.domain.member.entity.MemberFavorLecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MemberFavorRepository extends JpaRepository<MemberFavorLecture, Long>{

	Page<MemberFavorLecture> findAllByMemberId(Long id, Pageable pageable);
	MemberFavorLecture findByMemberIdAndLectureId(Long memberId, Long lectureId);
	boolean existsByMemberIdAndLectureId(Long memberId, Long lectureId);
}
