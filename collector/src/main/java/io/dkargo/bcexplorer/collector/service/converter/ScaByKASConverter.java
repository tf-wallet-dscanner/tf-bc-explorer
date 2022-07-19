package io.dkargo.bcexplorer.collector.service.converter;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.entity.Sca;
import io.dkargo.bcexplorer.dto.domain.kas.account.request.ReqScaDTO;
import io.dkargo.bcexplorer.dto.domain.kas.account.response.ResScaDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScaByKASConverter {

    // req -> sca
    public static Sca of(ReqScaDTO reqScaDTO) {

        String createAt = null;

        if (reqScaDTO.getCreateAt() != null) {
            createAt = reqScaDTO.getCreateAt();
        } else {
            createAt = CommonConverter.currentDateTime();
        }

        Sca.Result result = Sca.Result.builder()
                .accType(reqScaDTO.getResultByGetAccount().getAccType())
                .balance(reqScaDTO.getResultByGetAccount().getAccount().getBalance())
                // 계산
                .balanceToDouble(1004D)
                .humanReadable(reqScaDTO.getResultByGetAccount().getAccount().getHumanReadable())
                .nonce(reqScaDTO.getResultByGetAccount().getAccount().getNonce())
                // 계산
                .totalTransaction(1004L)
                .type(reqScaDTO.getResultByGetAccount().getAccount().getType())
                .codeFormat(reqScaDTO.getResultByGetAccount().getAccount().getCodeFormat())
                .codeHash(reqScaDTO.getResultByGetAccount().getAccount().getCodeHash())
                .storageRoot(reqScaDTO.getResultByGetAccount().getAccount().getStorageRoot())
                .build();

        return Sca.builder()
                .jsonrpc(reqScaDTO.getJsonrpc())
                .result(result)
                .createAt(createAt)
                .updateAt(CommonConverter.currentDateTime())
                .build();
    }

    // sca -> res
    public static ResScaDTO of(Sca sca) {

        ResScaDTO.Result result = ResScaDTO.Result.builder()
                .accType(sca.getResult().getAccType())
                .balance(sca.getResult().getBalance())
                .balanceToDouble(sca.getResult().getBalanceToDouble())
                .humanReadable(sca.getResult().getHumanReadable())
                .nonce(sca.getResult().getNonce())
                .totalTransaction(sca.getResult().getTotalTransaction())
                .type(sca.getResult().getType())
                .codeFormat(sca.getResult().getCodeFormat())
                .codeHash(sca.getResult().getCodeHash())
                .storageRoot(sca.getResult().getStorageRoot())
                .build();

        return ResScaDTO.builder()
                .id(sca.getId())
                .jsonrpc(sca.getJsonrpc())
                .result(result)
                .createAt(sca.getCreateAt())
                .updateAt(sca.getUpdateAt())
                .build();
    }
}
