package kr.kernel.teachme.domain.review.dto;

import lombok.Data;

@Data
public class ReviewRequest {
	private Long lectureId;
	private double score;
	private String content;
}
