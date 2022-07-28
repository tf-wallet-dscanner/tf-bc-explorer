package io.dkargo.bcexplorer.collector.service.converter;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.entity.Eoa;
import io.dkargo.bcexplorer.dto.domain.kas.account.request.ReqEoaDTO;
import io.dkargo.bcexplorer.dto.domain.kas.account.response.ResEoaDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EoaByKASConverter {

    // req -> eoa
    public static Eoa of(ReqEoaDTO reqEoaDTO) {

        Eoa.Result result = Eoa.Result.builder()
                .address(reqEoaDTO.getResultByGetAccount().getAccount().getAddress())
                .accType(reqEoaDTO.getResultByGetAccount().getAccType())
                .balance(reqEoaDTO.getResultByGetAccount().getAccount().getBalance())
                // 계산
                .balanceByFormat(reqEoaDTO.getResultByGetAccount().getAccount().getBalanceByFormat())
                .humanReadable(reqEoaDTO.getResultByGetAccount().getAccount().getHumanReadable())
                .nonce(reqEoaDTO.getResultByGetAccount().getAccount().getNonce())
                // 계산
                .totalTransaction(reqEoaDTO.getResultByGetAccount().getAccount().getTotalTransaction())
                .type(reqEoaDTO.getResultByGetAccount().getAccount().getType())
                .build();

        return Eoa.builder()
                .jsonrpc(reqEoaDTO.getJsonrpc())
                .result(result)
                .serviceCode(reqEoaDTO.getServiceCode())
                .createAt(CommonConverter.currentDateTime())
                .updateAt(CommonConverter.currentDateTime())
                .build();
    }

    // eoa -> res
    public static ResEoaDTO of(Eoa eoa) {

        ResEoaDTO.Result result = ResEoaDTO.Result.builder()
                .address(eoa.getResult().getAddress())
                .accType(eoa.getResult().getAccType())
                .balance(eoa.getResult().getBalance())
                .balanceByFormat(eoa.getResult().getBalanceByFormat())
                .humanReadable(eoa.getResult().getHumanReadable())
                .nonce(eoa.getResult().getNonce())
                .totalTransaction(eoa.getResult().getTotalTransaction())
                .type(eoa.getResult().getType())
                .build();

        return ResEoaDTO.builder()
                .id(eoa.getId())
                .jsonrpc(eoa.getJsonrpc())
                .result(result)
                .serviceCode(eoa.getServiceCode())
                .createAt(eoa.getCreateAt())
                .updateAt(eoa.getUpdateAt())
                .build();
    }
}
