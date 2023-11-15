package kr.kernel.teachme.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewReportResponse {
    private int reviewCount;
    private double averageScore;
}
