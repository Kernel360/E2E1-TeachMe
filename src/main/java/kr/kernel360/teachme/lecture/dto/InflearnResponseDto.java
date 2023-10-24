package kr.kernel360.teachme.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InflearnResponseDto {
	private String title;
	private String id;
	private int realIntPrice;
	private int saleIntPrice;
	private String instructor;
	private String description;
	private String skills;
}
