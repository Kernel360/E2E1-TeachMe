package kr.kernel.teachme.domain.crawler.controller;

import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.domain.crawler.dto.CrawlingRequest;
import kr.kernel.teachme.domain.crawler.dto.CrawlingResponse;
import kr.kernel.teachme.domain.crawler.service.FastcampusLectureDetailCrawlingService;
import kr.kernel.teachme.domain.crawler.service.FastcampusLectureListCrawlingService;
import kr.kernel.teachme.domain.crawler.service.InflearnLectureDetailCrawlingService;
import kr.kernel.teachme.domain.crawler.service.InflearnLectureListCrawlingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class CrawlerControllerTest {

    @Mock
    private InflearnLectureListCrawlingService inflearnLectureListCrawlingService;
    @Mock
    private FastcampusLectureListCrawlingService fastcampusLectureListCrawlingService;
    @Mock
    private InflearnLectureDetailCrawlingService inflearnLectureDetailCrawlingService;
    @Mock
    private FastcampusLectureDetailCrawlingService fastcampusLectureDetailCrawlingService;

    @InjectMocks
    private CrawlerController crawlerController;

    private CrawlingRequest crawlingRequest;

    @BeforeEach
    void setUp() {
        crawlingRequest = new CrawlingRequest();
    }

    @DisplayName("crawlLectureData가 fastcampus에 대해 잘 작동하는지")
    @Test
    void testCrawlLectureDataForFastcampus() {
        crawlingRequest.setPlatform("fastcampus");
        doNothing().when(fastcampusLectureListCrawlingService).runCrawler();

        ResponseEntity<CrawlingResponse> response = crawlerController.crawlLectureData(crawlingRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("크롤링 성공", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @DisplayName("crawlLectureData가 inflearn에 대해 잘 작동하는지")
    @Test
    void testCrawlLectureDataForInflearn() {
        crawlingRequest.setPlatform("inflearn");
        doNothing().when(inflearnLectureListCrawlingService).runCrawler();

        ResponseEntity<CrawlingResponse> response = crawlerController.crawlLectureData(crawlingRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("크롤링 성공", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @DisplayName("crawlLectureData에서 오류 발생시 예외가 잘 발생하는지")
    @Test
    void testCrawlLectureDataWithCrawlerException() {
        crawlingRequest.setPlatform("inflearn");
        doThrow(new CrawlerException("Test Exception")).when(inflearnLectureListCrawlingService).runCrawler();

        ResponseEntity<CrawlingResponse> response = crawlerController.crawlLectureData(crawlingRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("크롤링 실패: Test Exception"));
    }

    @DisplayName("crawlLectureDetail이 inflearn에 대해 잘 작동하는지")
    @Test
    void testCrawlLectureDetailForInflearn() {
        crawlingRequest.setPlatform("inflearn");
        doNothing().when(inflearnLectureDetailCrawlingService).runInflearnLectureDetailCrawler();

        ResponseEntity<CrawlingResponse> response = crawlerController.crawlLectureDetail(crawlingRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("크롤링 성공", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @DisplayName("crawlLectureDetail이 fastcampus에 대해 잘 작동하는지")
    @Test
    void testCrawlLectureDetailForFastcampus() {
        crawlingRequest.setPlatform("fastcampus");
        doNothing().when(fastcampusLectureDetailCrawlingService).update();

        ResponseEntity<CrawlingResponse> response = crawlerController.crawlLectureDetail(crawlingRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("크롤링 성공", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @DisplayName("crawlLectureDetail에서 오류 발생시 예외가 잘 발생하는지")
    @Test
    void crawlLectureDetailWithCrawlerException() {
        crawlingRequest.setPlatform("fastcampus");
        doThrow(new CrawlerException("Test Exception")).when(fastcampusLectureDetailCrawlingService).update();

        ResponseEntity<CrawlingResponse> response = crawlerController.crawlLectureDetail(crawlingRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).getMessage().contains("크롤링 실패: Test Exception"));
    }
}
