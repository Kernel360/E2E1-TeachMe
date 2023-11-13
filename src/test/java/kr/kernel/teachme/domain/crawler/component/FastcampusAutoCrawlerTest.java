package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureDetailResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FastcampusAutoCrawlerTest {

    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FastcampusAutoCrawler fastcampusAutoCrawler;

    private List<Lecture> lectures;

    @BeforeEach
    void setUp() {
        fastcampusAutoCrawler.BASE_URL = "https://fastcampus.co.kr/.api/www/courses/";
        lectures = new ArrayList<>();
        Lecture mockLecture = new Lecture();
        lectures.add(mockLecture);
    }

    @DisplayName("crawlLectureAutomatically 메서드가 잘 작동하는지")
    @Test
    void testCrawlLectureAutomatically() throws InterruptedException {
        FastcampusLectureDetailResponse mockResponse = new FastcampusLectureDetailResponse();
        FastcampusLectureDetailResponse.Data mockData = new FastcampusLectureDetailResponse.Data();
        FastcampusLectureDetailResponse.Course mockCourse = new FastcampusLectureDetailResponse.Course();
        mockData.setCourse(mockCourse);
        mockResponse.setData(mockData);
        when(restTemplate.getForObject(anyString(), eq(FastcampusLectureDetailResponse.class)))
                .thenReturn(mockResponse);

        when(lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc("fastcampus"))
                .thenReturn(lectures);

        fastcampusAutoCrawler.crawlLectureAutomatically();
        verify(lectureRepository).saveAll(any(List.class));
    }

    @DisplayName("fetchLecturesToUpdate 메서드가 잘 작동하는지")
    @Test
    void testFetchLecturesToUpdate() {
        when(lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc("fastcampus"))
                .thenReturn(lectures);

        List<Lecture> result = fastcampusAutoCrawler.fetchLecturesToUpdate();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(lectureRepository).findTop10ByPlatformOrderByLastCrawlDateAsc("fastcampus");
    }

    @DisplayName("updateLectureDetails 메서드가 잘 작동하는지")
    @Test
    void testUpdateLectureDetails() throws InterruptedException {
        FastcampusLectureDetailResponse mockResponse = new FastcampusLectureDetailResponse();
        FastcampusLectureDetailResponse.Data mockData = new FastcampusLectureDetailResponse.Data();
        FastcampusLectureDetailResponse.Course mockCourse = new FastcampusLectureDetailResponse.Course();

        mockData.setCourse(mockCourse);
        mockResponse.setData(mockData);

        when(restTemplate.getForObject(anyString(), eq(FastcampusLectureDetailResponse.class)))
                .thenReturn(mockResponse);

        List<Lecture> updatedLectures = fastcampusAutoCrawler.updateLectureDetails(lectures);

        assertNotNull(updatedLectures);
        assertEquals(lectures.size(), updatedLectures.size());
    }

    @DisplayName("saveUpdatedLectures 메서드가 잘 작동하는지")
    @Test
    void testSaveUpdatedLectures() {
        fastcampusAutoCrawler.saveUpdatedLectures(lectures);
        verify(lectureRepository).saveAll(lectures);
    }
}
