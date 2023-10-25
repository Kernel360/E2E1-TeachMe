package kr.kernel360.teachme.lecture.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InflearnRequest {

	@NotBlank
	private String title;

	@NotBlank
	private String imageSource;

	@NotBlank
	private int studentCnt;

	private int realIntPrice;

	private int saleIntPrice;

	@NotBlank
	private String instructor;

	@NotBlank
	private String url;

	@NotBlank
	private String description;

	@NotBlank
	private String skills;
}
