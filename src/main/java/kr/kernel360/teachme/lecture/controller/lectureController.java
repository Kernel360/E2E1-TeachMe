package kr.kernel360.teachme.lecture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lecture")
public class lectureController {
    @GetMapping("/view")
    public String viewLecture() {
        return "hi";
    }
}
