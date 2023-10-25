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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "inflearn")
public class Inflearn {

	private String title;

	@Column(columnDefinition =  "TEXT")
	private String imageSource;
	private int studentCnt;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int realIntPrice;
	private int saleIntPrice;
	private String instructor;

	@Column(columnDefinition =  "TEXT")
	private String url;
	private String description;
	private String skills;
}
