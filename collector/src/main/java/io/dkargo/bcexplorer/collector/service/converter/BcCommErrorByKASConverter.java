package io.dkargo.bcexplorer.collector.service.converter;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.entity.BcCommError;
import io.dkargo.bcexplorer.dto.domain.kas.bccommerror.request.ReqBcCommErrorDTO;
import io.dkargo.bcexplorer.dto.domain.kas.bccommerror.response.ResBcCommErrorDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BcCommErrorByKASConverter {

    // req -> bcCommError
    public static BcCommError of(ReqBcCommErrorDTO reqBcCommErrorDTO) {

        BcCommError.Error error = BcCommError.Error.builder()
                .code(reqBcCommErrorDTO.getError().getCode())
                .message(reqBcCommErrorDTO.getError().getMessage())
                .data(reqBcCommErrorDTO.getError().getData())
                .build();

        return BcCommError.builder()
                .errorId(reqBcCommErrorDTO.getId())
                .jsonrpc(reqBcCommErrorDTO.getJsonrpc())
                .error(error)
                .caverRequestType(reqBcCommErrorDTO.getCaverRequestType().getValue())
                .kasRequestType(reqBcCommErrorDTO.getKasRequestType().getValue())
                .rawResponse(reqBcCommErrorDTO.getRawResponse())
                .createAt(CommonConverter.currentDateTime())
                .build();
    }

    // bcCommError -> res
    public static ResBcCommErrorDTO of(BcCommError bcCommError) {

        ResBcCommErrorDTO.Error error = ResBcCommErrorDTO.Error.builder()
                .code(bcCommError.getError().getCode())
                .data(bcCommError.getError().getData())
                .message(bcCommError.getError().getMessage())
                .build();

        return ResBcCommErrorDTO.builder()
                .id(bcCommError.getId())
                .errorId(bcCommError.getErrorId())
                .jsonrpc(bcCommError.getJsonrpc())
                .error(error)
                .caverRequestType(bcCommError.getCaverRequestType())
                .kasRequestType(bcCommError.getKasRequestType())
                .rawResponse(bcCommError.getRawResponse())
                .createAt(bcCommError.getCreateAt())
                .build();
    }
}
