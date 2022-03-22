package com.whm.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 15718
 */
public class DateUtils {

    /**
     * 根据指定方式pattern 将date进行格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String formateDateTime(Date date, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
