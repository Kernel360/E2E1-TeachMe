package kr.kernel360.teachme.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.kernel360.teachme.lecture.entity.InflearnLecture;
import org.springframework.stereotype.Repository;

@Repository
public interface InflearnRepository extends JpaRepository<InflearnLecture, Long> {
}
