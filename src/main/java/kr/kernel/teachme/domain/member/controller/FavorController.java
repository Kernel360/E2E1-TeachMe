package kr.kernel.teachme.domain.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.domain.member.entity.MemberFavor;
import kr.kernel.teachme.domain.member.service.MemberFavorService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/favor")
public class FavorController {
	private final MemberFavorService memberFavorService;
	@ApiOperation(value="개인 페이지", notes="개인 페이지 찜 목록 출력")
	@GetMapping("/{memberId}")
	public String getFavorList(Model model, Long memberId) {
		List<MemberFavor> favorLectures = memberFavorService.getFavorLectureList(memberId);
		model.addAttribute("favorLectures", favorLectures);
		return "favor/{memberId}";
	}

	@ApiOperation(value="개인 페이지", notes="찜한 강의 삭제하기")
	@DeleteMapping("/delete")
	public String deleteFavorLecture(Model model, Long memberId, Long lectureId) {
		memberFavorService.deleteFavorLecture(memberId, lectureId);
		List<MemberFavor> favorLectures = memberFavorService.getFavorLectureList(memberId);
		model.addAttribute("favorLectures", favorLectures);
		return "favor";
	}
}
