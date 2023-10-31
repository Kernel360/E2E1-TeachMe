package kr.kernel.teachme.lecture.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "inflearn_lecture")
public class InflearnLecture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(columnDefinition =  "TEXT")
	private String imageSource;

	private int studentCnt;

	private int realIntPrice;

	private int saleIntPrice;

	private String instructor;

	@Column(columnDefinition =  "TEXT")
	private String url;

	@Column(columnDefinition =  "TEXT")
	private String description;

	private String skills;

	private int videoCnt;

	private int duration;

	@ColumnDefault("false")
	private boolean detailUploadFlag;

	private Date postDate;
	private Date updateDate;

	@Builder
	protected InflearnLecture(String title, String imageSource, int studentCnt, int realIntPrice, int saleIntPrice, String instructor, String url, String description, String skills, int videoCnt, int duration, boolean detailUploadFlag) {
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
		this.videoCnt = videoCnt;
		this.duration = duration;
		this.detailUploadFlag = detailUploadFlag;
	}

	public void updateDetailInfo(int videoCnt, int duration, String imageSource, Date postDate, Date updateDate) {
		this.videoCnt = videoCnt;
		this.duration = duration;
		this.imageSource = this.imageSource.isBlank() ? imageSource : this.imageSource;
		this.detailUploadFlag = true;
		this.postDate = postDate;
		this.updateDate = updateDate;
	}
}
