package kr.kernel.teachme.lecture.dto;

import javax.persistence.Entity;

import kr.kernel.teachme.lecture.entity.InflearnLecture;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InflearnLectureDetailResponse {
	private int videoCnt;
	private int duration;
}
