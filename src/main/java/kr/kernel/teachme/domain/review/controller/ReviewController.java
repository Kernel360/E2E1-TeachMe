package kr.kernel.teachme.domain.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;
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

	@ApiOperation(value="리뷰", notes="해당 강의 리뷰 출력")
	@GetMapping
	public void getReviewList(Model model, @RequestBody ReviewRequest request) {
		List<Review> reviews = reviewService.getLectureReviewList(request.getLectureId());
		model.addAttribute("reviews", reviews);
	}

	@ApiOperation(value="리뷰 등록", notes="강의 리뷰 등록")
	@PostMapping("/add")
	public ResponseEntity<ReviewResponse> addLectureReview(@RequestBody ReviewRequest request,
		@CookieValue (name = "JWT-AUTHENTICATION", required = true) String token) {
		ReviewResponse response = new ReviewResponse();
		try {
			reviewService.addLectureReiview(token, request.getLectureId(), request.getContent(), request.getScore());
			response.setMessage("리뷰 추가 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setMessage("리뷰 추가 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@ApiOperation(value="리뷰", notes="강의 리뷰 삭제")
	@DeleteMapping("/delete")
	public ResponseEntity<ReviewResponse> deleteLectureReview(@RequestBody ReviewRequest request,
		@CookieValue(name = "JWT-AUTHENTICATION", required = true) String token) {
		ReviewResponse response = new ReviewResponse();
		try {
			reviewService.deleteLectureReview(token, request.getLectureId());
			response.setMessage("리뷰 삭제 성공");
			return ResponseEntity.ok(response);
		} catch (Exception e){
			response.setMessage("리뷰 삭제 중 오류 발생");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
