package kr.kernel360.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel360.teachme.lecture.entity.Lecture;
import kr.kernel360.teachme.lecture.service.LectureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final LectureService lectureService;

    public HomeController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @ApiOperation(value="홈 화면", notes="홈 화면에 인기 강의 목록 출력")
    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Lecture> lectures = lectureService.getLatestLectures();
        model.addAttribute("lectures", lectures);
        return "home";
    }
}
