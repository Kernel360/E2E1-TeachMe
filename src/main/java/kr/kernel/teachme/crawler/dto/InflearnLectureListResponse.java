package kr.kernel.teachme.crawler.dto;

import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.util.StringUtil;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InflearnLectureListResponse {
	private String title;
	private String imageSource;
	private Long id;
	private int realIntPrice;
	private int saleIntPrice;
	private String instructor;
	private String url;
	private String description;
	private String skills;

	public Lecture toEntity() {
		return Lecture.builder()
			.platform("inflearn")
			.title(title)
			.img(imageSource)
			.price(realIntPrice)
			.discountPrice(saleIntPrice)
			.instructor(instructor)
			.url(url)
			.description(description)
			.keywords(skills)
			.build()
			;
	}


	// public Lecture toLectureEntity(){
	// 	return Lecture.builder()
	// 			.lectureId(id)
	// 			.platform("Inflearn")
	// 			.title(title)
	// 			.description(description)
	// 			.keywords(skills)
	// 			.url(url)
	// 			.img(imageSource)
	// 			.build();
	// }

}
