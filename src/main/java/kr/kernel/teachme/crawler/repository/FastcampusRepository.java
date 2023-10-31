package kr.kernel.teachme.crawler.repository;

import kr.kernel.teachme.crawler.entity.FastcampusLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FastcampusRepository extends JpaRepository<FastcampusLecture, Long> {
    List<FastcampusLecture> findAllByDetailUploadFlagFalse();
    boolean existsByDetailUploadFlagIsFalse();

}
