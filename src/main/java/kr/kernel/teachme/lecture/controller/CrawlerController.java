package kr.kernel.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.lecture.dto.CrawlingRequest;
import kr.kernel.teachme.lecture.service.FastcampusLectureListCrawlingService;
import kr.kernel.teachme.lecture.service.InflearnLectureListCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    private final InflearnLectureListCrawlingService inflearnLectureListCrawlingService;
    private final FastcampusLectureListCrawlingService fastcampusLectureListCrawlingService;

    @ApiOperation(value="어드민 크롤러 사이트", notes="crawlerForm.html return")
    @GetMapping("")
    public ModelAndView getCrawlerPage() {
        return new ModelAndView("crawler/crawlerForm");
    }

    @ApiOperation(value="어드민 크롤링 요청", notes="요청 Parameter에 따라 크롤러 작동")
    @PostMapping("/crawling")
    public ResponseEntity<Map<String, String>> crawlLectureData(@RequestBody CrawlingRequest crawling) {
        Map<String, String> response = new HashMap<>();
        if(crawling.getPlatform().equals("fastcampus")) {
            try {
                fastcampusLectureListCrawlingService.create();
                response.put("message", "크롤링 성공");
                return ResponseEntity.ok(response);
            } catch (CrawlerException e) {
                response.put("message", "크롤링 실패: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } else {
            try {
                inflearnLectureListCrawlingService.runInflearnLectureCrawler();
                response.put("message", "크롤링 성공");
                return ResponseEntity.ok(response);
            } catch (CrawlerException e) {
                response.put("message", "크롤링 실패: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

        }
    }
    @ApiOperation(value="어드민 상세 정보 크롤링 요청", notes="요청 Parameter에 따라 크롤러 작동")
    @PostMapping("/lecture")
    public void crawlLectureDetail(@RequestBody CrawlingRequest crawling) {
        Map<String, String> response = new HashMap<>();
        if (crawling.getPlatform().equals("inflearn")) {
            try {
                //inflearnLectureDetailCrawlingService.create();
                response.put("message", "크롤링 성공");
                //return ResponseEntity.ok(response);
            } catch (CrawlerException e) {
                response.put("message", "크롤링 실패: " + e.getMessage());
                //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }
}
