package kr.kernel.teachme.domain.lecture.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponse<T> {
	private T body;
	private Pagination pagination;

	public static <T> PaginationResponse<T> of(T content, int number, int size, int totalPages, int currentElements, Long totalElements) {
		return new PaginationResponse<>(content, new Pagination(number, size, currentElements, totalPages, totalElements));
	}
}


