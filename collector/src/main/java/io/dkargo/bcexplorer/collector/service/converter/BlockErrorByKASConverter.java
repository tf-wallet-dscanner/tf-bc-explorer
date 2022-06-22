package io.dkargo.bcexplorer.collector.service.converter;

import io.dkargo.bcexplorer.domain.entity.BlockError;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqBlockErrorDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResBlockErrorDTO;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class BlockErrorByKASConverter {

    // currentDate
    public static String currentDateTime() {

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, currentLocale);

        return simpleDateFormat.format(today);
    }

    //req -> blockError
    public static BlockError of(ReqBlockErrorDTO reqBlockErrorDTO) {

        return BlockError.builder()
                .blockId(reqBlockErrorDTO.getId())
                .jsonrpc(reqBlockErrorDTO.getJsonrpc())
                .code(reqBlockErrorDTO.getCode())
                .message(reqBlockErrorDTO.getMessage())
                .data(reqBlockErrorDTO.getData())
                .rawResponse(reqBlockErrorDTO.getRawResponse())
                .createAt(currentDateTime())
                .build();
    }

    //blockError -> res
    public static ResBlockErrorDTO of(BlockError blockError) {

        return ResBlockErrorDTO.builder()
                .id(blockError.getId())
                .blockId(blockError.getBlockId())
                .jsonrpc(blockError.getJsonrpc())
                .code(blockError.getCode())
                .message(blockError.getMessage())
                .data(blockError.getData())
                .rawResponse(blockError.getRawResponse())
                .build();
    }
}
