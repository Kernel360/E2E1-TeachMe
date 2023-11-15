package kr.kernel.teachme.domain.crawler.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.domain.crawler.dto.CrawlingResponse;
import kr.kernel.teachme.domain.crawler.service.FastcampusLectureDetailCrawlingService;
import kr.kernel.teachme.domain.crawler.service.FastcampusLectureListCrawlingService;
import kr.kernel.teachme.domain.crawler.service.InflearnLectureDetailCrawlingService;
import kr.kernel.teachme.domain.crawler.service.InflearnLectureListCrawlingService;
import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.domain.crawler.dto.CrawlingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    private final InflearnLectureListCrawlingService inflearnLectureListCrawlingService;
    private final FastcampusLectureListCrawlingService fastcampusLectureListCrawlingService;
    private final InflearnLectureDetailCrawlingService inflearnLectureDetailCrawlingService;
    private final FastcampusLectureDetailCrawlingService fastcampusLectureDetailCrawlingService;

    private static final String CRAWLING_SUCEED_MESSAGE = "크롤링 성공";
    private static final String CRAWLING_FAILURE_MESSAGE = "크롤링 실패: ";

    @ApiOperation(value="어드민 크롤러 사이트", notes="crawlerForm.html return")
    @GetMapping("")
    public ModelAndView getCrawlerPage() {
        return new ModelAndView("crawler/crawlerForm");
    }

    @ApiOperation(value="어드민 크롤링 요청", notes="요청 Parameter에 따라 크롤러 작동")
    @PostMapping("/crawling")

    public ResponseEntity<CrawlingResponse> crawlLectureData(@RequestBody CrawlingRequest crawling) {
        CrawlingResponse response = new CrawlingResponse();
        if(crawling.getPlatform().equals("fastcampus")) {
            try {
                fastcampusLectureListCrawlingService.runCrawler();
                response.setMessage(CRAWLING_SUCEED_MESSAGE);
                return ResponseEntity.ok(response);
            } catch (CrawlerException e) {
                response.setMessage(CRAWLING_FAILURE_MESSAGE + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            try {
                inflearnLectureListCrawlingService.runCrawler();
                response.setMessage(CRAWLING_SUCEED_MESSAGE);
                return ResponseEntity.ok(response);
            } catch (CrawlerException e) {
                response.setMessage(CRAWLING_FAILURE_MESSAGE + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }
  
    @ApiOperation(value="어드민 상세 정보 크롤링 요청", notes="요청 Parameter에 따라 크롤러 작동")
    @PostMapping("/detailCrawling")
    public ResponseEntity<CrawlingResponse>  crawlLectureDetail(@RequestBody CrawlingRequest crawling) {
        CrawlingResponse response = new CrawlingResponse();
        if (crawling.getPlatform().equals("inflearn")) {
            try {
                inflearnLectureDetailCrawlingService.runCrawler();
                response.setMessage(CRAWLING_SUCEED_MESSAGE);
                return ResponseEntity.ok(response);
            } catch (CrawlerException e) {
                response.setMessage(CRAWLING_FAILURE_MESSAGE + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            try {
                fastcampusLectureDetailCrawlingService.runCrawler();
                response.setMessage(CRAWLING_SUCEED_MESSAGE);
                return ResponseEntity.ok(response);
            } catch (CrawlerException e){
                response.setMessage(CRAWLING_FAILURE_MESSAGE + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            } catch (InterruptedException e) {
                throw new CrawlerException("크롤링 실패: ", e);
            }
        }
    }
}
