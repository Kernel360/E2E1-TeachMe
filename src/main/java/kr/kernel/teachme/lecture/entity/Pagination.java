package kr.kernel.teachme.lecture.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
	private Integer page;
	private Integer size;
	private Integer currentElements;
	private Integer totalPage;
	private Long totalElements;
}
