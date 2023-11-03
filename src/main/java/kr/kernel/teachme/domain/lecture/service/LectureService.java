package kr.kernel.teachme.domain.lecture.service;

import kr.kernel.teachme.domain.lecture.dto.PaginationResponse;
import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.dto.Pagination;

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

    public PaginationResponse<List<Lecture>> searchList(Pageable pageable, SearchRequest search) {
        Page<Lecture> page = lectureRepository.findBySearchOption(pageable, search);
        Pagination pagination = Pagination.builder()
            .page(page.getNumber())
            .size(page.getSize())
            .currentElements(page.getNumberOfElements())
            .totalElements(page.getTotalElements())
            .totalPage(page.getTotalPages())
            .build()
            ;

        PaginationResponse<List<Lecture>> response = PaginationResponse.<List<Lecture>>builder()
                .body(page.getContent())
                .pagination(pagination)
                .build();

        return response;
    }
    public Optional<Lecture> getLectureDetail(Long lectureId) {
        return lectureRepository.findById(lectureId);
    }
}
