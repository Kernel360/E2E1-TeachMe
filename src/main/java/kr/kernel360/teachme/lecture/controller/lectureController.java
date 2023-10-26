package kr.kernel360.teachme.lecture.controller;

import kr.kernel360.teachme.lecture.dto.FastcampustLectureResponse;
import kr.kernel360.teachme.lecture.entity.FastcampusLecture;
import kr.kernel360.teachme.lecture.service.FastcampusLectureListCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class lectureController {
//    @GetMapping("/view")
//    public String viewLecture() {
//        return "hi";
//    }

    private final FastcampusLectureListCrawlingService fastcampusLectureListCrawlingService;
    @GetMapping("/view")
    public List<FastcampustLectureResponse> viewLecture() {
        return fastcampusLectureListCrawlingService.create();
    }



}
