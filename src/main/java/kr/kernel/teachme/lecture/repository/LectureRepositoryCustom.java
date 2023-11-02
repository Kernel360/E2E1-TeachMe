package kr.kernel.teachme.lecture.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import kr.kernel.teachme.lecture.entity.Lecture;

@Repository
public interface LectureRepositoryCustom{
	List<Lecture> findBySearchOption(Pageable pageable, String filter, String sort, String option, String keyword);
}
