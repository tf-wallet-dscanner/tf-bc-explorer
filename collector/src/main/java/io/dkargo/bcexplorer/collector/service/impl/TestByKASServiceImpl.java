package io.dkargo.bcexplorer.collector.service.impl;

import com.klaytn.caver.methods.response.Account;
import com.klaytn.caver.methods.response.Addresses;
import com.klaytn.caver.methods.response.Quantity;
import io.dkargo.bcexplorer.collector.service.TestByKASService;
import io.dkargo.bcexplorer.core.converter.CommonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.Request;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestByKASServiceImpl implements TestByKASService {

    private final CaverExtKAS caverExtKAS;

    // 클라이언트가 소유한 계정의 주소 목록 확인
    public void getKlayAccountAddresses() {

        try {
//            Addresses addresses = caverExtKAS.rpc.klay.getAccounts().send();
            Request<?, Addresses> addresses = caverExtKAS.rpc.klay.getAccounts();
            log.info("addresses : {}", CommonConverter.objectToString(addresses));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 입력받은 주소의 계정 정보 확인 (Klaytn에는 스마트 컨트랙트 계쩡과 외부 소유 계정(EOA)이 있다.)
    public void getAccount(String address) {

        try {
            Account account = caverExtKAS.rpc.klay.getAccount(address).send();
            log.info("account : {}", CommonConverter.objectToString(account));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 어떤 주소의 계정에서 발신된 트랜잭션의 개수 확인
    public void getTransactionCount(String address) {

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getTransactionCount(address).send();
            log.info("quantity : {}", CommonConverter.objectToString(quantity));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 입력으로 받은 주소에 해당하는 계정의 잔액 확인
    public void getBalance(String address) {

        try {
            Quantity quantity = caverExtKAS.rpc.klay.getBalance(address).send();
            log.info("quantity : {}", CommonConverter.objectToString(quantity));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
