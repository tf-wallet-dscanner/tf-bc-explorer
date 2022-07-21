package io.dkargo.bcexplorer.core.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class CommonConverter {

    // 10 진수 (Long) -> 16 진수
    public static String longToHex(Long value) {

        return "0x" + Long.toHexString(value);
    }

    // 16 진수 -> 10 진수 (Long)
    public static Long hexToLong(String hexadecimal) {

        return Long.decode(hexadecimal);
    }

    // 16 진수 -> 10 진수 (BigInteger) --- amount, balance에 사용 ---
    public static BigDecimal hexToBigDecimal(String hexadecimal) {

        BigInteger decimal = new BigInteger(hexadecimal.substring(2), 16);

        BigDecimal bigDecimal1 = new BigDecimal(decimal);
        BigDecimal bigDecimal2 = new BigDecimal(0.000000000000000001);

        // 소수 점 8째 자리까지 표현 (다른 로직에서 해줌)
        // return bigDecimalMul.setScale(8, BigDecimal.ROUND_HALF_UP);
        return bigDecimal1.multiply(bigDecimal2);
    }

    // 16 진수 -> 10 진수 (Float - Klay Unit)
    public static Float hexToKlayUnit(String hexadecimal) {

        Long value = Long.decode(hexadecimal);

        return (float) (value * 0.000000000000000001);
    }

    // Object -> String
    public static String objectToString(Object object) {

        String objectToString = null;

        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            objectToString = ow.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectToString;
    }

    // String -> Object
    public static Object stringToObject(String string, Class<?> object) {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = new JSONObject();

        try {
            Object obj = jsonParser.parse(string);
            jsonObj = (JSONObject) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObj;
    }

    // String -> LocalDateTime
    public static LocalDateTime stringToLocalDateTime(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);

        return localDateTime;
    }

    // Timestamp -> String (Data Format - Asia/Seoul)
    public static String timestampToString(Long timestamp) {

        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(date);
    }

    // CurrentDate (String)
    public static String currentDateTime() {

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, currentLocale);

        return simpleDateFormat.format(today);
    }

    // Float -> String (Format - #.########)
    public static String floatToFormatString(Float value) {

        DecimalFormat df = new DecimalFormat("#.########");

        return df.format(value);
    }

    // Double -> String (Format - #.########)
    public static String doubleToFormatString(Double value) {

        DecimalFormat df = new DecimalFormat("#.########");

        return df.format(value);
    }

    // Big Decimal -> String (Format - #.########)
    public static String bigDecimalToFormatString(BigDecimal value) {

        DecimalFormat df = new DecimalFormat("#.########");

        return df.format(value);
    }

}
