package kr.kernel.teachme.domain.lecture.controller;

import kr.kernel.teachme.domain.lecture.service.LectureService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureControllerTest {

    @InjectMocks
    private LectureController lectureController;

    @Mock
    private LectureService lectureService;


}
