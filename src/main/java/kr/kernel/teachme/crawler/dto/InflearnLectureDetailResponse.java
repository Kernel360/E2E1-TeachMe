package kr.kernel.teachme.crawler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class InflearnLectureDetailResponse {
	private boolean deletedFlag = false;
	private int videoCnt;
	private int duration;
	private String imageSource;
	private Date postDate;
	private Date updateDate;

	public void setInflearnInfoToData(String info) {
		Pattern pattern = Pattern.compile("총 (\\d+)개 수업(?: \\((\\d+)시간(?: (\\d+)분)?\\))?");
		Matcher matcher = pattern.matcher(info);
		int video = 0;
		int hours = 0;
		int minutes = 0;

		if (matcher.find()) {
			video = Integer.parseInt(matcher.group(1));
			hours = (matcher.group(2) == null) ? 0 : Integer.parseInt(matcher.group(2));
			minutes = (matcher.group(3) == null) ? 0 : Integer.parseInt(matcher.group(3));
		} else {
			throw new IllegalArgumentException();
		}
		this.videoCnt = video;
		this.duration = (hours * 60) + minutes;
	}

	public void setDateFromString (String postDate, String updateDate) throws ParseException {
		String datePattern = "\\d{4}년 \\d{2}월 \\d{2}일";

		Pattern datePatternMatcher = Pattern.compile(datePattern);

		Matcher postDateMatcher = datePatternMatcher.matcher(postDate);
		Matcher updateDateMatcher = datePatternMatcher.matcher(updateDate);

		if (postDateMatcher.find()) {
			String postDateMatch = postDateMatcher.group();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
			this.postDate = sdf.parse(postDateMatch);
		}

		if (updateDateMatcher.find()) {
			String updateDateMatch = updateDateMatcher.group();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
			this.updateDate = sdf.parse(updateDateMatch);
		}
	}
}
