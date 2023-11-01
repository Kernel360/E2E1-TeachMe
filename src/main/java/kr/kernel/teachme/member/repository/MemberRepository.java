package kr.kernel.teachme.member.repository;

import kr.kernel.teachme.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String name);
}