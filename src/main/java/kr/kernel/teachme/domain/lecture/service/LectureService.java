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
        Page<Lecture> page = findLectures(pageable, search);
        Pagination pagination = createPagination(page);
        return buildPaginationResponse(page, pagination);
    }

    private Page<Lecture> findLectures(Pageable pageable, SearchRequest searchRequest) {
        return lectureRepository.findBySearchOption(pageable, searchRequest);
    }

    private Pagination createPagination(Page<Lecture> page) {
        return Pagination.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .currentElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }

    private PaginationResponse<List<Lecture>> buildPaginationResponse(Page<Lecture> page, Pagination pagination) {
        return PaginationResponse.<List<Lecture>>builder()
                .body(page.getContent())
                .pagination(pagination)
                .build();
    }

    public Optional<Lecture> getLectureDetail(Long lectureId) {
        return lectureRepository.findById(lectureId);
    }
}
