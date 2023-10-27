package kr.kernel360.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/lecture")
public class LectureController {

    @ApiOperation(value="강의 리스트 사이트", notes="강의 리스트 출력 및 검색")
    @GetMapping("/list")
    public String getLectureListForm() {
        return "lecture/list";
    }


}
