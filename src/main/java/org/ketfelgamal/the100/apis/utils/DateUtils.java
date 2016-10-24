package org.ketfelgamal.the100.apis.utils;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

public class DateUtils {
    public DateUtils() {
        super();
    }
    
    public static Date parse(String dateStr){
        return DatatypeConverter.parseDateTime(dateStr).getTime();
    }
    
    public static String format(Date date){
        //2016-10-28T14:15:00.000-07:00
        SimpleDateFormat patternFormatter =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"){
            public StringBuffer format(Date date, StringBuffer toAppendTo,
                                       java.text.FieldPosition pos) {
                StringBuffer toFix = super.format(date, toAppendTo, pos);
                return toFix.insert(toFix.length() - 2, ':');
            }
        };
        return patternFormatter.format(date);
        
    }
}
