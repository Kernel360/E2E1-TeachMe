package kr.kernel.teachme.domain.lecture.dto;

import lombok.*;

@Data
@Builder
public class Pagination {
	private Integer page;
	private Integer size;
	private Integer currentElements;
	private Integer totalPage;
	private Long totalElements;
}
