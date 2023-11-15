package kr.kernel.teachme.common.util;

import java.time.*;
import java.util.Date;

public class DateUtil {

    public static Date convertLocalDateTimeToDate(LocalDateTime dttm) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(dttm.atZone(defaultZoneId).toInstant());
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

}
