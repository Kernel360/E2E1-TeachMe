package kr.kernel360.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel360.teachme.exception.CrawlerException;
import kr.kernel360.teachme.lecture.dto.CrawlingRequest;
import kr.kernel360.teachme.lecture.service.InflearnLectureListCrawlingService;
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

    @ApiOperation(value="어드민 크롤러 사이트", notes="crawlerForm.html return")
    @GetMapping("")
    public ModelAndView getCrawlerPage() {
        return new ModelAndView("crawler/crawlerForm");
    }

    @ApiOperation(value="어드민 크롤링 요청", notes="요청 Parameter에 따라 크롤러 작동")
    @PostMapping("/crawling")
    public ResponseEntity<String> crawlLectureData(@RequestBody CrawlingRequest crawling) {
        if(crawling.getPlatform().equals("fastcampus")) {

        } else {
            try {
                inflearnLectureListCrawlingService.runInflearnLectureCrawler();
                return ResponseEntity.ok("크롤링 성공");
            } catch (CrawlerException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("크롤링 실패: " + e.getMessage());
            }

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("크롤링 실패: 요청 양식이 맞지 않습니다.");
    }

}
