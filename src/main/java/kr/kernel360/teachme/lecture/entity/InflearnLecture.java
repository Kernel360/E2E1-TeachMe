package kr.kernel360.teachme.lecture.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "inflearn_lecture")
public class InflearnLecture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long uniqueId;

	private String title;

	@Column(columnDefinition =  "TEXT")
	private String imageSource;

	private int studentCnt;

	private int realIntPrice;

	private int saleIntPrice;

	private String instructor;

	@Column(columnDefinition =  "TEXT")
	private String url;

	private String description;

	private String skills;

	@Builder
	protected InflearnLecture(Long uniqueId, String title, String imageSource, int studentCnt, int realIntPrice, int saleIntPrice, String instructor, String url, String description, String skills) {
		this.uniqueId = uniqueId;
		Assert.hasLength(title, "Inflearn lecture title must not be empty");
		this.title = title;
		this.imageSource = imageSource;
		this.studentCnt = studentCnt;
		this.realIntPrice = realIntPrice;
		this.saleIntPrice = saleIntPrice;
		this.instructor = instructor;
		Assert.hasLength(url, "Inflearn lecture url must not be empty");
		this.url = url;
		this.description = description;
		this.skills = skills;
	}
}
