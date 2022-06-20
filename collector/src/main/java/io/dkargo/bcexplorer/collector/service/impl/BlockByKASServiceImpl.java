package io.dkargo.bcexplorer.collector.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.klaytn.caver.methods.response.Block;
import com.klaytn.caver.methods.response.BlockTransactionReceipts;
import com.klaytn.caver.methods.response.Quantity;
import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.dto.collector.response.ResGetLatestBlockNumberDTO;
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
public class BlockByKASServiceImpl implements BlockByKASService {

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

    public static int hexToLong(String hexadecimal) {

        return Integer.decode(hexadecimal);
    }

    public static String timestampToString(Long timestamp) {

        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(date);
    }

    @Override
    public ResGetLatestBlockNumberDTO getLatestBlockNumber() {

        long blockNumber = 0;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockNumber().send();
            log.info("quantity : {}", objectToString(quantity));

            blockNumber = hexToLong(quantity.getResult());
            log.info("fdsafdsafd :{}", blockNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetLatestBlockNumberDTO(blockNumber);
    }

    @Override
    public void getBlockByBlockNumber(Long blockNumber) {

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByNumber(blockNumber).send();
            log.info("block : {}", objectToString(block));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBlockReceiptByBlockHash(String blockHash) {

        try {
            BlockTransactionReceipts blockTransactionReceipts = caverExtKAS.rpc.klay.getBlockReceipts(blockHash).send();
            log.info("blockTransactionReceipts : {}", objectToString(blockTransactionReceipts));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
