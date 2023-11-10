package kr.kernel.teachme.domain.lecture.controller;

import kr.kernel.teachme.domain.lecture.dto.PaginationResponse;
import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.service.LectureService;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.service.MemberFavorService;
import kr.kernel.teachme.domain.review.entity.Review;
import kr.kernel.teachme.domain.review.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureControllerTest {

    @InjectMocks
    private LectureController lectureController;

    @Mock
    private LectureService lectureService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private MemberFavorService memberFavorService;

    @Mock
    private Model model;

    @Mock
    private PaginationResponse paginationResponse;

    @DisplayName("showLectureList 메서드가 페이지 DTO 응답을 잘 반환하는지")
    @Test
    void showLectureListShouldAddPaginationResponseModel() {
        int page = 1;
        SearchRequest searchRequest = new SearchRequest(); // 필요한 경우 SearchRequest에 적절한 필드 추가
        Page<Lecture> expectedPage = new PageImpl<>(Collections.singletonList(new Lecture()));
        PaginationResponse<List<Lecture>> paginationResponse = PaginationResponse.of(
                expectedPage.getContent(), expectedPage.getNumber(), expectedPage.getSize(), expectedPage.getTotalPages(), expectedPage.getNumberOfElements(), expectedPage.getTotalElements()
        );
        when(lectureService.searchList(any(PageRequest.class), any(SearchRequest.class))).thenReturn(paginationResponse);

        String viewName = lectureController.showLectureList(page, model, searchRequest);

        verify(model).addAttribute("lecturePage", paginationResponse);
        assertEquals("lecture/list", viewName);
    }

    @DisplayName("showLectureDetail 메서드가 강의 상세 정보를 잘 반환하는지")
    @Test
    void showLectureDetailShouldAddLectureDetailsToModel() {
        Long lectureId = 1L;
        Lecture expectedLecture = new Lecture();
        when(lectureService.getLectureDetail(lectureId)).thenReturn(Optional.of(expectedLecture));
        Member member = new Member("username", "password", "ROLE_USER", "name");
        when(memberFavorService.isFavorLecture(member, lectureId)).thenReturn(true);
        Page<Review> reviewPage = null;
        when(reviewService.getLectureReviewList(PageRequest.of(0, 5), lectureId)).thenReturn(reviewPage);

        String viewName = lectureController.showLectureDetail(lectureId, model, member, 0);

        verify(model).addAttribute("isFavor", true);
        verify(model).addAttribute("lecture", expectedLecture);
        verify(model).addAttribute("reviews", reviewPage);
        assertEquals("lecture/detail", viewName);
    }
}
