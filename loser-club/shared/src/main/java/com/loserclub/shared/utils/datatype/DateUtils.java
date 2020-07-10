package com.loserclub.shared.utils.datatype;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    /**
     * 普通时间格式
     */
    public static String PATTERN_DATE = "yyyy-MM-dd";
    public static String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static String PATTERN_TIME = "HH:mm:ss";

    public static DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE).withZone(ZoneId.systemDefault());
    public static DateTimeFormatter FORMATTER_DATETIME = DateTimeFormatter.ofPattern(PATTERN_DATETIME).withZone(ZoneId.systemDefault());
    public static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern(PATTERN_TIME).withZone(ZoneId.systemDefault());

    /**
     * 返回时间
     *
     * @param text 字符串
     * @return
     */
    public static LocalDateTime parseDateTime(String text) {

        if (StringUtils.isBlank(text) || "null".equals(text.trim()))
            return null;

        text = StringUtils.trim(text);

        if (text.length() >= 16 && text.length() <= 18)
            //形如 2015-03-19T02:23
            //形如 2015-03-19T02:23XX
            text = text.substring(0, 10) + " " + text.substring(11, 16) + ":00";
        if (text.length() >= 19) {
            //形如 2015-03-19T02:23:00.000Z
            //形如 2015-03-19T02:23:00Z
            //形如 2015-03-19T02:23:00
            text = text.substring(0, 10) + " " + text.substring(11, 19);
        }

        return LocalDateTime.parse(text, DateUtils.FORMATTER_DATETIME);
    }

    /**
     * 返回自定义格式化工具
     *
     * @param pattern 模式
     * @return
     */
    public static LocalDateTime parseDateTime(String text, String pattern) {
        if (StringUtils.isBlank(text) || StringUtils.isBlank(pattern)
                || "null".equals(text.trim()) || "null".equals(pattern)) {
            return null;
        }
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault()));
    }

    public static LocalDate parseDate(String text) {
        if (StringUtils.isBlank(text) || "null".equals(text.trim()))
            return null;

        text = StringUtils.trim(text);
        if (text.length() >= 10) {
            text = text.substring(0, 10);
        }
        return LocalDate.parse(text, DateUtils.FORMATTER_DATE);
    }

    public static LocalTime parseTime(String text) {
        if (StringUtils.isBlank(text) || "null".equals(text.trim()))
            return null;

        text = StringUtils.trim(text);
        if (text.length() == 8) {

        } else if (text.length() >= 19) {
            //形如 2015-03-19T02:23:00.000Z
            text = text.substring(12, 19);
        }

        return LocalTime.parse(text, DateUtils.FORMATTER_TIME);
    }

    public static Date toDate(LocalDateTime ldt) {

        if (ldt != null) {
            return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public static Date toDate(LocalTime lt) {
        if (lt != null) {
            return Date.from(lt.atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public static Date toDate(LocalDate ld) {

        if (ld != null) {
            return Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public static LocalDateTime toLocalDateTime(Date d) {
        if (d != null) {
            return LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }

    public static LocalDate toLocalDate(Date d) {
        if (d != null) {
            return LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public static LocalTime toLocalTime(Date d) {
        if (d != null) {
            return LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault()).toLocalTime();
        }
        return null;
    }

    public static Date parse(String text, String pattern) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {
            return formatter.parse(text);
        } catch (ParseException e) {
        }
        return null;
    }

    public static LocalDateTime parseAll(String text, String pattern) {

        Date in = parse(text, pattern);
        if (in != null) {
            return LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        }

        return null;
    }
}
