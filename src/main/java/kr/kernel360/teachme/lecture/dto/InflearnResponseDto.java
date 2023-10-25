package kr.kernel360.teachme.lecture.dto;

import kr.kernel360.teachme.lecture.util.StringUtil;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InflearnResponseDto {
	private String title;
	private String imageSource;
	private int studentCnt;
	private String id;
	private int realIntPrice;
	private int saleIntPrice;
	private String instructor;
	private String url;
	private String description;
	private String difficulty;
	private String skills;

	public void setStudentCnt(String studentCnt) {
		this.studentCnt = StringUtil.isNumeric(studentCnt) ? Integer.parseInt(studentCnt) : 0;
	}
}
