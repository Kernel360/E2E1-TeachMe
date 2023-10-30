package kr.kernel.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.lecture.entity.Api;
import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.service.LectureService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/all")
    @ResponseBody
    public Api<List<Lecture>> list(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
        Pageable pageable
    ){
        return lectureService.all(pageable);
    }
}
