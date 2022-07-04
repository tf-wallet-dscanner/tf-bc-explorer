package io.dkargo.bcexplorer.collector.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CommonConverter {

    // 16 진수 -> 10 진수 (Long)
    public static Long hexToLong(String hexadecimal) {

        return Long.decode(hexadecimal);
    }

    // 16 진수 -> 10 진수 (BigInteger) - amount 표현
    public static BigDecimal hexToBigDecimal(String hexadecimal) {

        BigInteger decimal = new BigInteger(hexadecimal.substring(2), 16);

        BigDecimal bigDecimal1 = new BigDecimal(decimal);
        BigDecimal bigDecimal2 = new BigDecimal(0.000000000000000001);

        // 소수 점 8째 자리까지 표현 (다른 로직에서 해줌)
        // return bigDecimalMul.setScale(8, BigDecimal.ROUND_HALF_UP);
        return bigDecimal1.multiply(bigDecimal2);
    }

    // hex -> klay unit
    public static Float hexToKlayUnit(String hexadecimal) {

        Long value = Long.decode(hexadecimal);

        return (float) (value * 0.000000000000000001);
    }

    // timestamp -> data format
    public static String timestampToString(Long timestamp) {

        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(date);
    }

    // object -> string
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

    // string -> object
    public static JSONObject stringToObject(String string) {

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

    // currentDate
    public static String currentDateTime() {

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, currentLocale);

        return simpleDateFormat.format(today);
    }

    // float -> string format
    public static String floatToFormatString(Float value) {

        DecimalFormat df = new DecimalFormat("#.########");

        return df.format(value);
    }

    // big decimal -> string format
    public static String bigDecimalToFormatString(BigDecimal value) {

        DecimalFormat df = new DecimalFormat("#.########");

        return df.format(value);
    }

}
