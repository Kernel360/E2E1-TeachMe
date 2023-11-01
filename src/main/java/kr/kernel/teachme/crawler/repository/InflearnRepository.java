package kr.kernel.teachme.crawler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.kernel.teachme.crawler.entity.InflearnLecture;

import org.springframework.stereotype.Repository;

@Repository
public interface InflearnRepository extends JpaRepository<InflearnLecture, Long> {
	boolean existsByDetailUploadFlagIsFalse();
	List<InflearnLecture> findAllByDetailUploadFlagIsFalse();
}
