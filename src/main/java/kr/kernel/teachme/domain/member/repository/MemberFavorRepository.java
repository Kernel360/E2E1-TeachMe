package kr.kernel.teachme.domain.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.kernel.teachme.common.jwt.JwtUtils;
import kr.kernel.teachme.domain.member.entity.MemberFavor;
@Repository
public interface MemberFavorRepository extends JpaRepository<MemberFavor, Long>{

	List<MemberFavor> findAllByMemberId(Long id);
	MemberFavor findByMemberIdAndLectureId(Long memberId, Long LectureId);
}
