package io.dkargo.bcexplorer.collector.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.klaytn.caver.methods.response.Transaction;
import io.dkargo.bcexplorer.collector.service.TransactionByKASService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionByKASServiceImpl implements TransactionByKASService {

    private final CaverExtKAS caverExtKAS;

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

    public static Long hexToLong(long hexadecimal) {

        return Long.parseLong(Long.toHexString(hexadecimal), 16);
    }

    public static String timestampToString(Long timestamp) {

        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(date);
    }

    @Override
    public void getTransactionByHash(String hash) {

        try {
            Transaction transaction = caverExtKAS.rpc.klay.getTransactionByHash(hash).send();
            log.info("transaction : {}", objectToString(transaction));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
