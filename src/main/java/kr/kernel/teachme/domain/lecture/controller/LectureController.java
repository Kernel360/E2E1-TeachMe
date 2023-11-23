package kr.kernel.teachme.domain.lecture.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.service.LectureService;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.member.service.MemberFavorService;
import kr.kernel.teachme.domain.review.entity.Review;
import kr.kernel.teachme.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lecture")
public class LectureController {

    private final LectureService lectureService;
    private final MemberFavorService memberFavorService;
    private final ReviewService reviewService;

    @ApiOperation(value="강의 리스트 사이트", notes="강의 리스트 출력 및 검색")
    @GetMapping("/list")
    public String showLectureList(@RequestParam(defaultValue = "1") int page, Model model, @ModelAttribute SearchRequest search) {
        Pageable pageable = PageRequest.of(page -1, 10, Sort.Direction.DESC, "id");
        Page<Lecture> lectureApiList = lectureService.searchList(pageable, search);
        model.addAttribute("lecturePage", lectureApiList);
        return "lecture/list";
    }

    @ApiOperation(value="강의 상세 정보 사이트", notes="강의 상세 정보 출력")
    @GetMapping("/{lectureId}")
    public String showLectureDetail(@PathVariable Long lectureId, Model model,
        @AuthenticationPrincipal Member member,
        @RequestParam(value = "page", defaultValue = "0") int page) {
        if(member != null) {
            boolean isFavorLecture = memberFavorService.isFavorLecture(member, lectureId);
            model.addAttribute("isFavor", isFavorLecture);
        }

        Optional<Lecture> lecture = lectureService.getLectureDetail(lectureId);
        Pageable pageable = PageRequest.of(page, 5);
        Page<Review> reviewApiList = reviewService.getLectureReviewList(pageable, lectureId);
        model.addAttribute("reviews", reviewApiList);
        model.addAttribute("lecture", lecture.orElseThrow(IllegalArgumentException::new));
        return "lecture/detail";
    }
}
