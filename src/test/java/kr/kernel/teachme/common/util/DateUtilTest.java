package kr.kernel.teachme.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilTest {

    @DisplayName("LocalDateTime이 Date로 잘 변환되는지")
    @Test
    void testConvertLocalDateTimeToDate() {
        LocalDateTime now = LocalDateTime.now();
        Date dateFromUtil = DateUtil.convertLocalDateTimeToDate(now);

        Date expectedDate = new Date();
        long timeDifference = Math.abs(dateFromUtil.getTime() - expectedDate.getTime());

        assertTrue(timeDifference < 1000, "LocalDateTime을 Date로 변환 시 시간 차이가 너무 큽니다.");
    }
}
