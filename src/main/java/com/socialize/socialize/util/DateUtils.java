package com.socialize.socialize.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class DateUtils {

  public static final ZoneId GMT_ZONE_ID = ZoneId.of("GMT");

  public static final DateTimeFormatter gmtFormatter = DateTimeFormatter.ISO_DATE_TIME;

  public static String getGmtZonedDateTime(ZonedDateTime zonedDateTime) {
    // gmtFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    // ZonedDateTime zonedDateTime = ZonedDateTime.now(GMT_ZONE_ID);
    return zonedDateTime.format(gmtFormatter);
  }

  public static Long diffDatesInDays(Date d1, Date d2) {
    if (Objects.nonNull(d1) && Objects.nonNull(d2)) {
      LocalDate localDate1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate localDate2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

      return ChronoUnit.DAYS.between(localDate1, localDate2);
    }
    return null;
  }

  public static LocalDate getLocalDate(Date date) {
    if (date != null) {
      return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    return null;
  }

  public static Date addHoursToDate(Date date, int hours) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, hours);
    return calendar.getTime();
  }

  public static String convertMilesSecondToSpecificDateFormat(
      long miliSeconds, String format, TimeZone timeZone) {
    String date = DateFormatUtils.format(miliSeconds, format, timeZone);
    return date;
  }

  public static Date stringToDate(String date, String datetimeFormat) throws ParseException {
    try {
      return new SimpleDateFormat(datetimeFormat).parse(date);
    } catch (ParseException e) {
      throw e;
    }
  }

  public static String getDateInSpecifiedFormat(Date date, String pattern) {
    LocalDate localDate = convertToLocalDate(date);
    return localDate.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static LocalDate convertToLocalDate(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDateTime convertToLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static LocalDate convertToLocalDateFromSqlDate(java.sql.Date sqlDate) {
    return sqlDate.toLocalDate();
  }
}
