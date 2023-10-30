package kr.kernel360.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel360.teachme.lecture.entity.Lecture;
import kr.kernel360.teachme.lecture.entity.Api;
import kr.kernel360.teachme.lecture.service.LectureService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final LectureService lectureService;

    @ApiOperation(value="홈 화면", notes="홈 화면에 인기 강의 목록 출력")
    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Lecture> lectures = lectureService.getLatestLectures();
        model.addAttribute("lectures", lectures);
        return "home";
    }
}
