package kr.kernel360.teachme.lecture.controller;

import kr.kernel360.teachme.lecture.dto.CrawlingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @GetMapping("")
    public ModelAndView getCrawlerPage() {
        ModelAndView mv = new ModelAndView("crawler/crawlerForm");
        return mv;
    }

    @PostMapping("/crawling")
    public String crawlLectureData(@RequestBody CrawlingRequest crawling) {
        if(crawling.getPlatform().equals("fastcampus")) {

        } else {

        }
        return "success";
    }

}
