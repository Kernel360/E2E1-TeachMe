package kr.kernel.teachme.domain.lecture.repository;

import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import kr.kernel.teachme.domain.lecture.entity.Lecture;

@Repository
public interface LectureRepositoryCustom{
	Page<Lecture> findBySearchOption(Pageable pageable, SearchRequest search);
}
