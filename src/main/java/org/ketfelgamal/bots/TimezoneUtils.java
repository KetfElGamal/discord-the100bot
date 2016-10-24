package org.ketfelgamal.bots;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Waseem on 17-Oct-16.
 */
public class TimezoneUtils {
    private static final String DATE_FORMAT = "EEE MMM dd hh:mm a yyyy z";

    public static String formatDate(Date date, String timezoneString){
        //System.out.println(ZoneId.systemDefault());
        //System.out.println(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        //System.out.println(formatter.format(date.toInstant().atZone(ZoneId.systemDefault())));

        ZonedDateTime localDateTime= date.toInstant().atZone(ZoneId.systemDefault());


        ZoneId zoneId = ZoneId.of(timezoneString);
        //System.out.println("TimeZone : " + zoneId);

        ZoneOffset currentOffsetForMyZone = zoneId.getRules().getOffset(localDateTime.toInstant());

        ZonedDateTime newDate = localDateTime.withZoneSameInstant(currentOffsetForMyZone);


        //ZonedDateTime zonedDate = ldt.atZone(zoneId);
        //System.out.println("Date ("+timezoneString+") : " + newDate);

        //System.out.println(formatter.format(newDate));
        String result = formatter.format(newDate);
        if(result.indexOf("Z") > 0)
            result = result.replace("Z","UTC");
        return result;
    }

    public static void main(String[] args){
        System.out.println(TimezoneUtils.formatDate(new Date(),"Europe/London"));

        System.out.println(TimezoneUtils.formatDate(new Date(),"America/New_York"));
    }
}
