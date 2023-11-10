package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class FastcampusAutoCrawlerTest {

    @InjectMocks
    private FastcampusAutoCrawler fastcampusAutoCrawler;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ModelMapper modelMapper;

    private Lecture mockLecture;

    @BeforeEach
    void setUp() {
        mockLecture = new Lecture(123L, "platform", "title", "description", "keywords", "url", "img", 0, 0, "inst", new Date(), new Date());

        List<Lecture> lectureList = Collections.singletonList(mockLecture);
        when(lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc(anyString())).thenReturn(lectureList);
    }

    @DisplayName("fetchLecturesToUpdate 메서드가 잘 작동하는지")
    @Test
    void fetchLecturesToUpdateTest() {
        List<Lecture> result = fastcampusAutoCrawler.fetchLecturesToUpdate();

        assertEquals(1, result.size());
        assertEquals(mockLecture, result.get(0));
    }
}
