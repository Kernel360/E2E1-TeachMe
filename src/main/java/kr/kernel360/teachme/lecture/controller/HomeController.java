package kr.kernel360.teachme.lecture.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @ApiOperation(value="홈 화면", notes="홈 화면에 인기 강의 목록 출력")
    @GetMapping("/")
    public String getHomePage(Model model) {
        return "home";
    }
}
