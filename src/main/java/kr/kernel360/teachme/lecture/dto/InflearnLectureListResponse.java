package kr.kernel360.teachme.lecture.dto;

import kr.kernel360.teachme.lecture.entity.InflearnLecture;
import kr.kernel360.teachme.lecture.util.StringUtil;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InflearnLectureListResponse {
	private String title;
	private String imageSource;
	private int studentCnt;
	private Long id;
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

	public InflearnLecture toEntity() {
		InflearnLecture inflearn = InflearnLecture.builder()
			.title(title)
			.imageSource(imageSource)
			.studentCnt(studentCnt)
			.uniqueId(id)
			.realIntPrice(realIntPrice)
			.saleIntPrice(saleIntPrice)
			.instructor(instructor)
			.url(url)
			.description(description)
			.skills(skills)
			.build()
			;
		return inflearn;
	}
}
