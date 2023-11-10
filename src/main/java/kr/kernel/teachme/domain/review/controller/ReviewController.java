package kr.kernel.teachme.domain.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.domain.member.entity.Member;
import kr.kernel.teachme.domain.review.dto.ReviewRequest;
import kr.kernel.teachme.domain.review.dto.ReviewResponse;
import kr.kernel.teachme.domain.review.entity.Review;
import kr.kernel.teachme.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
	private final ReviewService reviewService;

	@ApiOperation(value="리뷰", notes="자신이 작성한 강의 리뷰 출력")
	@GetMapping("/review")
	public String getReviewList(Model model,
		@AuthenticationPrincipal Member member,
		@RequestParam(value = "page", defaultValue = "0") int page) {
		if (member == null) return "redirect:/login";

		Pageable pageable = PageRequest.of(page, 5);
		Page<Review> reviews = reviewService.getMemberReviewList(pageable, member);
		model.addAttribute("reviews", reviews);
		return "member/review";
	}

	@ApiOperation(value="리뷰 등록", notes="강의 리뷰 등록")
	@PostMapping("/add")
	public ResponseEntity<ReviewResponse> addLectureReview(@RequestBody ReviewRequest request,
		@AuthenticationPrincipal Member member) {
		ReviewResponse response = new ReviewResponse();
		try {
			reviewService.addLectureReview(member, request.getLectureId(), request.getContent(), request.getScore());
			response.setMessage("리뷰 추가 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setMessage("리뷰 추가 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ApiOperation(value="리뷰 수정", notes="강의 리뷰 수정")
	@PostMapping("/update")
	public ResponseEntity<ReviewResponse> updateLectureReview(@RequestBody ReviewRequest request,
		@AuthenticationPrincipal Member member) {
		ReviewResponse response = new ReviewResponse();
		try {
			reviewService.updateLectureReview(member, request.getLectureId(), request.getContent(), request.getScore());
			response.setMessage("리뷰 수정 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setMessage("리뷰 수정 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ApiOperation(value="리뷰", notes="강의 리뷰 삭제")
	@DeleteMapping("/delete")
	public ResponseEntity<ReviewResponse> deleteLectureReview(@RequestBody ReviewRequest request,
		@AuthenticationPrincipal Member member) {
		ReviewResponse response = new ReviewResponse();
		try {
			reviewService.deleteLectureReview(member, request.getLectureId());
			response.setMessage("리뷰 삭제 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e){
			response.setMessage("리뷰 삭제 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
