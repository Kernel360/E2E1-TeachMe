package kr.kernel.teachme.domain.member.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.member.dto.FavorRequest;
import kr.kernel.teachme.domain.member.dto.FavorResponse;
import kr.kernel.teachme.domain.member.service.MemberFavorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/favor")
public class FavorController {
	private final MemberFavorService memberFavorService;
	@ApiOperation(value="개인 페이지", notes="개인 페이지 찜 목록 출력")
	@GetMapping("/list")
	public String getFavorList(Model model,
							   @CookieValue(name = "JWT-AUTHENTICATION", required = false) String token) {
		if(token.isEmpty()) return "redirect:/login";
		List<Lecture> favorLectures = memberFavorService.getFavorLectureList(token);
		model.addAttribute("favorLectures", favorLectures);
		return "favor/list";
	}

	@ApiOperation(value="찜 목록 추가", notes="member_favor_lecture 테이블에 데이터 추가")
	@PostMapping("/add")
	public ResponseEntity<FavorResponse> addFavorLecture(@RequestBody FavorRequest request, @CookieValue(name = "JWT-AUTHENTICATION", required = true) String token) {
		FavorResponse response = new FavorResponse();
		try {
			memberFavorService.addFavorLecture(token, request.getLectureId());
			response.setMessage("찜 목록 추가 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setMessage("찜 목록 추가 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ApiOperation(value="개인 페이지", notes="찜한 강의 삭제하기")
	@DeleteMapping("/delete")
	public ResponseEntity<FavorResponse> deleteFavorLecture(@RequestBody FavorRequest request,
								   @CookieValue(name = "JWT-AUTHENTICATION", required = true) String token) {
		FavorResponse response = new FavorResponse();
		try {
			memberFavorService.deleteFavorLecture(token, request.getLectureId());
			response.setMessage("찜 목록 삭제 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setMessage("찜 목록 삭제 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
}
