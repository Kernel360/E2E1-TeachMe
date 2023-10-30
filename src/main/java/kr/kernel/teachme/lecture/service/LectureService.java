package kr.kernel.teachme.lecture.service;

import kr.kernel.teachme.lecture.entity.Api;
import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.entity.Pagination;
import kr.kernel.teachme.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lecture> getLatestLectures() {
        return lectureRepository.findByOrderByIdDesc(PageRequest.of(0, 12));
    }

    public Api<List<Lecture>> all(Pageable pageable) {
        var list = lectureRepository.findAll(pageable);

        var pagination = Pagination.builder()
            .page(list.getNumber())
            .size(list.getSize())
            .currentElements(list.getNumberOfElements())
            .totalElements(list.getTotalElements())
            .totalPage(list.getTotalPages())
            .build()
            ;

        var response = Api.<List<Lecture>>builder()
            .body(list.toList())
            .pagination(pagination)
            .build();

        return response;
    }

}
