package io.dkargo.bcexplorer.dto.api.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public class CommonConverter {

    // 16 진수 -> 10 진수
    public static Long hexToLong(String hexadecimal) {

        return Long.decode(hexadecimal);
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

    // string -> localDateTime
    public static LocalDateTime stringToLocalDateTime(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);

        return localDateTime;
    }
}
