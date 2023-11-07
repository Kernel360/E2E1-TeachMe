package kr.kernel.teachme.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static Date convertLocalDateTimeToDate(LocalDateTime dttm) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(dttm.atZone(defaultZoneId).toInstant());
    }

}
