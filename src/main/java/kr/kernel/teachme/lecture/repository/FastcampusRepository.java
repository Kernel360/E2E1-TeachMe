package kr.kernel.teachme.lecture.repository;

import kr.kernel.teachme.lecture.entity.FastcampusLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FastcampusRepository extends JpaRepository<FastcampusLecture, Long> {
}
