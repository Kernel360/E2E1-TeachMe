package kr.kernel.teachme.domain.lecture.service;

import kr.kernel.teachme.domain.lecture.dto.PaginationResponse;
import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("getLatestLectures 메서드가 잘 작동하는지")
    @Test
    void getLatestLecturesTest() {
        when(lectureRepository.findByOrderByIdDesc(any(PageRequest.class)))
                .thenReturn(Collections.singletonList(new Lecture()));

        List<Lecture> result = lectureService.getLatestLectures();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(lectureRepository).findByOrderByIdDesc(any(PageRequest.class));
    }

    @DisplayName("searchList 메서드가 잘 작동하는지")
    @Test
    void searchListTest() {
        when(lectureRepository.findBySearchOption(any(Pageable.class), any(SearchRequest.class)))
                .thenReturn(Page.empty());

        PaginationResponse<List<Lecture>> result = lectureService.searchList(PageRequest.of(0, 10), new SearchRequest());

        assertNotNull(result);
        verify(lectureRepository).findBySearchOption(any(Pageable.class), any(SearchRequest.class));
    }

    @DisplayName("getLectureDetailTest 메서드가 잘 작동하는지")
    @Test
    void getLectureDetailTest() {
        Long lectureId = 1L;
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(new Lecture()));

        Optional<Lecture> result = lectureService.getLectureDetail(lectureId);

        assertTrue(result.isPresent());
        verify(lectureRepository).findById(lectureId);
    }
}
