package kr.kernel360.teachme.lecture.controller;

import kr.kernel360.teachme.lecture.dto.CrawlingRequest;
import kr.kernel360.teachme.lecture.service.InflearnLectureListCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    private final InflearnLectureListCrawlingService inflearnLectureListCrawlingService;

    @GetMapping("")
    public ModelAndView getCrawlerPage() {
        return new ModelAndView("crawler/crawlerForm");
    }

    @PostMapping("/crawling")
    public String crawlLectureData(@RequestBody CrawlingRequest crawling) {
        if(crawling.getPlatform().equals("fastcampus")) {

        } else {
            inflearnLectureListCrawlingService.runInflearnLectureCrawler();
        }
        return "success";
    }

}
