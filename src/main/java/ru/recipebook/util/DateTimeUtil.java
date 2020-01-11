package ru.recipebook.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final Date MIN_DATE = new GregorianCalendar(1, Calendar.FEBRUARY,1).getTime();
    private static final Date MAX_DATE = new GregorianCalendar(4000,Calendar.DECEMBER,31).getTime();

    private DateTimeUtil() {
    }

    public static @Nullable
    LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static @Nullable
    LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }

    public static Date getStartInclusive(Date date) {
        return date != null ? date : MIN_DATE;
    }

    public static Date getEndExclusive(Date date) {
        return date != null ? date : MAX_DATE;
    }
}

