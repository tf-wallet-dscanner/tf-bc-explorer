package io.dkargo.bcexplorer.collector.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import io.dkargo.bcexplorer.dto.collector.kas.transaction.response.ResGetTransactionReceiptByHashDTO;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.*;

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

    // list object -> list string
    public static List<String> listObjectToListString(List<ResGetTransactionReceiptByHashDTO> resGetTransactionReceiptByHashDTOS) {

        List<String> listObjectToListString = new ArrayList<>();

        for(ResGetTransactionReceiptByHashDTO resGetTransactionReceiptByHashDTO : resGetTransactionReceiptByHashDTOS) {

//            // result
//            objectToString(resGetTransactionReceiptByHashDTO.getResult());
//
//            // result -> feePayerSignatures
//            objectToString(resGetTransactionReceiptByHashDTO.getResult().getFeePayerSignatures());
//            // error
//            objectToString(resGetTransactionReceiptByHashDTO.getError());

            listObjectToListString.add(objectToString(resGetTransactionReceiptByHashDTO));
        }

        log.info("jjjjjj : {} ", listObjectToListString);

        return listObjectToListString;
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
}
