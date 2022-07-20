package io.dkargo.bcexplorer.collector.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klaytn.caver.methods.response.Account;
import io.dkargo.bcexplorer.collector.service.AccountByKASService;
import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.repository.EoaRepository;
import io.dkargo.bcexplorer.domain.repository.ScaRepository;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetEoaDTO;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetScaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountByKASServiceImpl implements AccountByKASService {

    private final CaverExtKAS caverExtKAS;

    private final EoaRepository eoaRepository;
    private final ScaRepository scaRepository;

    private static ResGetAccountDTO getAccount(Account account, String address) {

        ResGetAccountDTO.Result result = null;
        if(account.getResult() != null) {

            // result -> account 생성
            ResGetAccountDTO.Result.Account accountInResult = null;
            if(account.getResult().getAccount() != null) {

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                // Account -> result -> account 값을 하나하나 get 해오지 못해서, gson 사용 (EOA와 SCA 응답 파라미터가 다름)
                switch (account.getResult().getAccount().getType().toString()) {

                    case "EOA" :
                        log.info("EOA!!");
                        ResGetEoaDTO resGetEoaDTO = gson.fromJson(CommonConverter.objectToString(account.getResult().getAccount()), ResGetEoaDTO.class);

                        accountInResult = ResGetAccountDTO.Result.Account.builder()
                                .address(address)
                                .balance(resGetEoaDTO.getBalance())
                                .humanReadable(resGetEoaDTO.getHumanReadable())
                                .nonce(resGetEoaDTO.getNonce())
                                .type(resGetEoaDTO.getType())
                                .build();
                        break;

                    case "SCA" :
                        log.info("SCA");
                        ResGetScaDTO resGetScaDTO = gson.fromJson(CommonConverter.objectToString(account.getResult().getAccount()), ResGetScaDTO.class);

                        accountInResult = ResGetAccountDTO.Result.Account.builder()
                                .address(address)
                                .balance(resGetScaDTO.getBalance())
                                .humanReadable(resGetScaDTO.getHumanReadable())
                                .nonce(resGetScaDTO.getNonce())
                                .type(resGetScaDTO.getType())
                                .codeFormat(resGetScaDTO.getCodeFormat())
                                .codeHash(resGetScaDTO.getCodeHash())
                                .storageRoot(resGetScaDTO.getStorageRoot())
                                .build();
                        break;

                    default :
                        break;
                }
            }

            // result 생성
            result = ResGetAccountDTO.Result.builder()
                    .accType(account.getResult().getAccType())
                    .account(accountInResult)
                    .build();
        }

        // error 생성
        ResGetAccountDTO.Error error = null;
        if(account.getError() != null) {

            error = ResGetAccountDTO.Error.builder()
                    .code(account.getError().getCode())
                    .message(account.getError().getMessage())
                    .data(account.getError().getData())
                    .build();
        }

        // response 생성
        ResGetAccountDTO resGetAccountDTO = ResGetAccountDTO.builder()
                .id(account.getId())
                .jsonrpc(account.getJsonrpc())
                .result(result)
                .error(error)
                .rawResponse(account.getRawResponse())
                .build();

        return resGetAccountDTO;

    }

    public ResGetAccountDTO getAccountByAddress(String address) {

        ResGetAccountDTO resGetAccountDTO = null;

        try {
            Account account = caverExtKAS.rpc.klay.getAccount(address).send();
            log.info("account : {}", CommonConverter.objectToString(account));

            resGetAccountDTO = getAccount(account, address);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resGetAccountDTO;
    }
}
