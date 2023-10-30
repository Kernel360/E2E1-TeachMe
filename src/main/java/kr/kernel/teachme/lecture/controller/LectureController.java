package kr.kernel.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.lecture.entity.Api;
import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lecture")
public class LectureController {

    private final LectureService lectureService;

    @ApiOperation(value="강의 리스트 사이트", notes="강의 리스트 출력 및 검색")
    @GetMapping("/list")
    public String getLectureListForm(@RequestParam(defaultValue = "1") int page, Model model) {
        Pageable pageable = PageRequest.of(page -1, 10, Sort.Direction.DESC, "id");
        Api<List<Lecture>> lectureApiList = lectureService.all(pageable);
        model.addAttribute("lecturePage", lectureApiList);
        return "lecture/list";
    }


}
