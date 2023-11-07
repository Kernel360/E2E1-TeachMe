package kr.kernel.teachme.domain.member.repository;

import kr.kernel.teachme.domain.member.entity.MemberFavorLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberFavorRepository extends JpaRepository<MemberFavorLecture, Long>{

	List<MemberFavorLecture> findAllByMemberId(Long id);
	MemberFavorLecture findByMemberIdAndLectureId(Long memberId, Long lectureId);
	boolean existsByMemberIdAndLectureId(Long memberId, Long lectureId);
}
