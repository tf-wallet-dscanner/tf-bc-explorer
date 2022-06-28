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

    // current date
    public static String currentDateTime() {

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, currentLocale);

        return simpleDateFormat.format(today);
    }

    //req -> blockError
    public static BlockError of(ReqBlockErrorDTO reqBlockErrorDTO) {

        BlockError.Error error = BlockError.Error.builder()
                .code(reqBlockErrorDTO.getError().getCode())
                .message(reqBlockErrorDTO.getError().getMessage())
                .data(reqBlockErrorDTO.getError().getData())
                .build();

        return BlockError.builder()
                .errorId(reqBlockErrorDTO.getId())
                .jsonrpc(reqBlockErrorDTO.getJsonrpc())
                .error(error)
                .kasRequestType(reqBlockErrorDTO.getKasRequestType().getValue())
                .rawResponse(reqBlockErrorDTO.getRawResponse())
                .createAt(currentDateTime())
                .build();
    }

    //blockError -> res
    public static ResBlockErrorDTO of(BlockError blockError) {

        ResBlockErrorDTO.Error error = ResBlockErrorDTO.Error.builder()
                .code(blockError.getError().getCode())
                .data(blockError.getError().getData())
                .message(blockError.getError().getMessage())
                .build();

        return ResBlockErrorDTO.builder()
                .id(blockError.getId())
                .errorId(blockError.getErrorId())
                .jsonrpc(blockError.getJsonrpc())
                .error(error)
                .kasRequestType(blockError.getKasRequestType())
                .rawResponse(blockError.getRawResponse())
                .createAt(blockError.getCreateAt())
                .build();
    }
}
