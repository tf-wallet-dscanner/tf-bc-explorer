package io.dkargo.bcexplorer.collector.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.klaytn.caver.methods.response.Block;
import com.klaytn.caver.methods.response.BlockTransactionReceipts;
import com.klaytn.caver.methods.response.BlockWithConsensusInfo;
import com.klaytn.caver.methods.response.Quantity;
import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.dto.collector.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.collector.response.ResGetBlockTransactionCountDTO;
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

    public static Long hexToLong(String hexadecimal) {

        return Long.decode(hexadecimal);
    }

    public static String timestampToString(Long timestamp) {

        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        return simpleDateFormat.format(date);
    }

    @Override
    public ResGetLatestBlockNumberDTO getLatestBlockNumber() {

        long blockNumber = 0L;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockNumber().send();
            log.info("quantity : {}", objectToString(quantity));

            blockNumber = hexToLong(quantity.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetLatestBlockNumberDTO(blockNumber);
    }

    @Override
    public ResGetBlockDTO getBlockByNumber(Long blockNumber) {

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByNumber(blockNumber).send();
            log.info("block : {}", objectToString(block));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockDTO(blockNumber);
    }

    @Override
    public void getBlockByHash(String blockHash) {

        try {
            Block block = caverExtKAS.rpc.klay.getBlockByHash(blockHash).send();
            log.info("block : {}", objectToString(block));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBlockReceiptByHash(String blockHash) {

        try {
            BlockTransactionReceipts blockTransactionReceipts = caverExtKAS.rpc.klay.getBlockReceipts(blockHash).send();
            log.info("blockTransactionReceipts : {}", objectToString(blockTransactionReceipts));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBlockWithConsensusInfoByNumber(Long blockNumber) {

        try {
            BlockWithConsensusInfo blockWithConsensusInfo = caverExtKAS.rpc.klay.getBlockWithConsensusInfoByNumber(blockNumber).send();
            log.info("blockWithConsensusInfo : {}", objectToString(blockWithConsensusInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBlockWithConsensusInfoByHash(String blockHash) {

        try {
            BlockWithConsensusInfo blockWithConsensusInfo = caverExtKAS.rpc.klay.getBlockWithConsensusInfoByHash(blockHash).send();
            log.info("blockWithConsensusInfo : {}", objectToString(blockWithConsensusInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResGetBlockTransactionCountDTO getBlockTransactionCountByNumber(Long blockNumber) {

        long transactionCount = 0L;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockTransactionCountByNumber(blockNumber).send();
            log.info("quantity : {}", objectToString(quantity));

            transactionCount = hexToLong(quantity.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockTransactionCountDTO(transactionCount);
    }

    @Override
    public ResGetBlockTransactionCountDTO getBlockTransactionCountByHash(String blockHash) {

        long transactionCount = 0L;

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBlockTransactionCountByHash(blockHash).send();
            log.info("quantity : {}", objectToString(quantity));

            transactionCount = hexToLong(quantity.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResGetBlockTransactionCountDTO(transactionCount);
    }
}
