package kr.kernel.teachme.domain.lecture.dto;

import lombok.*;

@Data
@Builder
public class PaginationResponse<T> {
	private T body;
	private Pagination pagination;
}
