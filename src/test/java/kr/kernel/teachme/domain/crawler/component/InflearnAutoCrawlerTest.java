package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InflearnAutoCrawlerTest {

    @InjectMocks
    private InflearnAutoCrawler inflearnAutoCrawler;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private Document mockDocument;

    @DisplayName("crawlLectureAutomatically 메서드가 잘 작동하는지")
    @Test
    void testCrawlLectureAutomatically() throws IOException, ParseException, InterruptedException {
        when(lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc("inflearn")).thenReturn(new ArrayList<>());

        inflearnAutoCrawler.crawlLectureAutomatically();

        verify(lectureRepository, times(1)).findTop10ByPlatformOrderByLastCrawlDateAsc("inflearn");
        verify(lectureRepository, times(1)).saveAll(anyList());
    }

}
