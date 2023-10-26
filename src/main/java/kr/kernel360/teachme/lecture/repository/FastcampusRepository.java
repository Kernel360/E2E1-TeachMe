package kr.kernel360.teachme.lecture.repository;

import kr.kernel360.teachme.lecture.entity.FastcampusLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FastcampusRepository extends JpaRepository<FastcampusLecture, Long> {
}
