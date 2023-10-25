package kr.kernel360.teachme.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.kernel360.teachme.lecture.entity.Inflearn;

public interface InflearnRepository extends JpaRepository<Inflearn, Long> {
}
