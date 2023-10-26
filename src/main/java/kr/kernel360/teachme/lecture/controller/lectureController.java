package kr.kernel360.teachme.lecture.controller;

import kr.kernel360.teachme.lecture.dto.FastcampustLectureResponse;
import kr.kernel360.teachme.lecture.entity.FastcampusLecture;
import kr.kernel360.teachme.lecture.service.FastcampusLectureListCrawlingService;
import lombok.RequiredArgsConstructor;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class lectureController {

    private final FastcampusLectureListCrawlingService fastcampusLectureListCrawlingService;
    @GetMapping("/view")
    public List<FastcampustLectureResponse> viewLecture() {
        return fastcampusLectureListCrawlingService.create();


    @ApiOperation(value="강의 크롤링을 위한 사이트", notes="정상 동작 시 페이지 return")
    @GetMapping("/admin")
    public String viewTest() {
        return "hi";
    }



}
