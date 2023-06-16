package com.example.jobis.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

@Slf4j
public class BigDecimalUtil {

    public static BigDecimal fromCommaStr(String commaAmount){
        DecimalFormat dFmt = new DecimalFormat("###,###,###");
        dFmt.setParseBigDecimal(true);
        BigDecimal result = new BigDecimal(0);

        try {
            result = (BigDecimal) dFmt.parse(commaAmount);
        } catch (ParseException e) {
            log.error("BigDecimalUtil.fromCommaStr Error :: {}", commaAmount);
        }

        return result;
    }

    public static String toCommaStr(BigDecimal amount){
        DecimalFormat dFmt = new DecimalFormat("###,###,###");
        String result = dFmt.format(amount);

        return result;
    }

}
