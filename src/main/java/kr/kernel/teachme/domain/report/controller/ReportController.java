package kr.kernel.teachme.domain.report.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ReportController {

    @ApiOperation(value="리뷰 데이터 조회 사이트", notes="리뷰 레포트 데이터 조회")
    @GetMapping("/report")
    public String showReportData() {
        return "report/report";
    }
}
