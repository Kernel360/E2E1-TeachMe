package kr.kernel.teachme.lecture.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;

import kr.kernel.teachme.lecture.entity.InflearnLecture;

@Data
@NoArgsConstructor
public class InflearnLectureDetailResponse {
	private int videoCnt;
	private int duration;
	private String imageSource;

	public void setInflearnInfoToData(String info) {
		Pattern pattern = Pattern.compile("총 (\\d+)개 수업 \\((\\d+)시간 (\\d+)분\\)");
		Matcher matcher = pattern.matcher(info);
		int videoCnt = 0;
		int hours = 0;
		int minutes = 0;

		if (matcher.find()) {
			videoCnt = Integer.parseInt(matcher.group(1));
			hours = Integer.parseInt(matcher.group(2));
			minutes = Integer.parseInt(matcher.group(3));
		} else {
			throw new IllegalArgumentException();
		}
		this.videoCnt = videoCnt;
		this.duration = (hours * 60) + minutes;
	}
}
