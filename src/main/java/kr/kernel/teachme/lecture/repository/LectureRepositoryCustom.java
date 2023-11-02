package kr.kernel.teachme.lecture.repository;

import java.util.List;

import kr.kernel.teachme.lecture.dto.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import kr.kernel.teachme.lecture.entity.Lecture;

@Repository
public interface LectureRepositoryCustom{
	Page<Lecture> findBySearchOption(Pageable pageable, SearchRequest search);
}
