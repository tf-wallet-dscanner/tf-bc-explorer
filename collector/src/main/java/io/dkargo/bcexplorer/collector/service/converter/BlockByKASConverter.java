package io.dkargo.bcexplorer.collector.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.dkargo.bcexplorer.domain.entity.Block;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResBlockDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResGetBlockWithConsensusInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public class BlockByKASConverter {

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

    // req -> block
    public static Block of(ReqBlockDTO reqBlockDTO) {

        return Block.builder()
                .blockId(reqBlockDTO.getId())
                .jsonrpc(reqBlockDTO.getJsonrpc())
                .error(objectToString(reqBlockDTO.getError())) // string 으로 변환 후 build
                .rawResponse(reqBlockDTO.getRawResponse())
                .blockScore(reqBlockDTO.getBlockScore())
                .totalBlockScore(reqBlockDTO.getTotalBlockScore())
                .committee(reqBlockDTO.getCommittee())
                .gasLimit(reqBlockDTO.getGasLimit())
                .gasUsed(reqBlockDTO.getGasUsed())
                .hash(reqBlockDTO.getHash())
                .miner(reqBlockDTO.getMiner())
                .nonce(reqBlockDTO.getNonce())
                .number(reqBlockDTO.getNumber())
                .parentBlockHash(reqBlockDTO.getParentBlockHash())
                .proposer(reqBlockDTO.getProposer())
                .receiptsRoot(reqBlockDTO.getReceiptsRoot())
                .size(reqBlockDTO.getSize())
                .stateRoot(reqBlockDTO.getStateRoot())
                .timestamp(reqBlockDTO.getTimestamp())
                .timestampFoS(reqBlockDTO.getTimestampFoS())
                .transactionsRoot(reqBlockDTO.getTransactionsRoot())
                .createAt(currentDateTime())
                .build();
    }

    // block -> res
    public static ResBlockDTO of(Block block) {

        JSONObject jsonObj = stringToObject(block.getError());
        ResGetBlockWithConsensusInfoDTO.Error error = ResGetBlockWithConsensusInfoDTO.Error.builder()
                .code((Integer) jsonObj.get("code"))
                .message((String) jsonObj.get("message"))
                .data((String) jsonObj.get("data"))
                .build();

        return ResBlockDTO.builder()
                .id(block.getId())
                .blockId(block.getBlockId())
                .jsonrpc(block.getJsonrpc())
                .error(error) // string을 객체형태로 변환 후 build
                .rawResponse(block.getRawResponse())
                .blockScore(block.getBlockScore())
                .totalBlockScore(block.getTotalBlockScore())
                .committee(block.getCommittee())
                .gasLimit(block.getGasLimit())
                .gasUsed(block.getGasUsed())
                .hash(block.getHash())
                .miner(block.getMiner())
                .nonce(block.getNonce())
                .number(block.getNumber())
                .parentBlockHash(block.getParentBlockHash())
                .proposer(block.getProposer())
                .receiptsRoot(block.getReceiptsRoot())
                .size(block.getSize())
                .stateRoot(block.getStateRoot())
                .timestamp(block.getTimestamp())
                .timestampFoS(block.getTimestampFoS())
                .transactionsRoot(block.getTransactionsRoot())
                .build();
    }
}
