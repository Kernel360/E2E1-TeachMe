package kr.kernel360.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/lecture")
public class LectureController {

    @ApiOperation(value="테스트 사이트", notes="정상 동작 시 페이지 return")
    @GetMapping("/admin")
    public String viewTest() {
        return "hi";
    }

}
