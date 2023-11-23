package kr.kernel.teachme.domain.lecture.controller;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.service.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Mock
    private LectureService lectureService;

    @Mock
    private Model model;

    private List<Lecture> mockLectures;

    @BeforeEach
    void setUp() {
        mockLectures = Arrays.asList(new Lecture(), new Lecture());
        when(lectureService.getLatestLectures()).thenReturn(mockLectures);
    }

    @DisplayName("홈페이지 컨트롤러가 잘 작동하는지")
    @Test
    void testShowHomePage() {
        String viewName = homeController.showHomePage(model);

        verify(model).addAttribute("lectures", mockLectures);
        assertEquals("home", viewName);
    }
}
