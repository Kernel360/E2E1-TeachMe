package kr.kernel.teachme.domain.lecture.service;

import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import kr.kernel.teachme.domain.lecture.entity.Lecture;

import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lecture> getLatestLectures() {
        return lectureRepository.findByOrderByIdDesc(PageRequest.of(0, 12));
    }

    public Page<Lecture> searchList(Pageable pageable, SearchRequest search) {
        return findLectures(pageable, search);
    }

    private Page<Lecture> findLectures(Pageable pageable, SearchRequest searchRequest) {
        return lectureRepository.findBySearchOption(pageable, searchRequest);
    }



    public Optional<Lecture> getLectureDetail(Long lectureId) {
        return lectureRepository.findById(lectureId);
    }
}
