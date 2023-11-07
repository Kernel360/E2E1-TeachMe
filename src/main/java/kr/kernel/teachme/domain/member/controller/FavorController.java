package kr.kernel.teachme.domain.member.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.member.entity.MemberFavor;
import kr.kernel.teachme.domain.member.service.MemberFavorService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/favor")
public class FavorController {
	private final MemberFavorService memberFavorService;
	@ApiOperation(value="개인 페이지", notes="개인 페이지 찜 목록 출력")
	@GetMapping("/list")
	public String getFavorList(Model model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie ck : cookies) {
			if (ck.getName().equals("JWT-AUTHENTICATION")) {
				token = ck.getValue();
			}
		}
		if (token == null) {
			System.out.println("token is null");
		}
		List<Lecture> favorLectures = memberFavorService.getFavorLectureList(token);
		model.addAttribute("favorLectures", favorLectures);
		return "favor/list";
	}

	@ApiOperation(value="개인 페이지", notes="찜한 강의 삭제하기")
	@DeleteMapping("/delete")
	public void deleteFavorLecture(Long lectureId, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie ck : cookies) {
			if (ck.getName().equals("JWT-AUTHENTICATION")) {
				token = ck.getValue();
			}
		}
		if (token == null) {
			System.out.println("token is null");
		}
		memberFavorService.deleteFavorLecture(token, lectureId);
	}
}
