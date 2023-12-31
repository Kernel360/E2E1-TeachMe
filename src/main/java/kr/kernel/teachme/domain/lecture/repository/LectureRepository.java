package kr.kernel.teachme.domain.lecture.repository;

import kr.kernel.teachme.domain.lecture.entity.Lecture;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {

    List<Lecture> findByOrderByIdDesc(Pageable pageable);
    void deleteByUrl(String url);
    List<Lecture> findAllByDetailUploadFlagIsFalseAndPlatform(String platform);
    int countByPlatform(String platform);
    boolean existsByDetailUploadFlagIsFalseAndPlatform(String platform);

    List<Lecture> findTop10ByPlatformOrderByLastCrawlDateAsc(String platform);
}
