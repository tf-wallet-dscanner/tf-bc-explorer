package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.TestByKASService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-by-kas")
public class TestByKASController {


    private final TestByKASService testByKASService;

    @ApiOperation(
            value = "최신의 블록 클라이언트가 소유한 계정의 주소 목록 확인 조회",
            notes = "getKlayAccountAddresses"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
    })
    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public void getKlayAccountAddresses() {

        testByKASService.getKlayAccountAddresses();
    }

    @ApiOperation(
            value = "입력받은 주소의 계정 정보 확인 (Klaytn에는 스마트 컨트랙트 계쩡과 외부 소유 계정(EOA)이 있다.)",
            notes = "getAccount"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
    })
    @GetMapping("/accounts/{address}")
    @ResponseStatus(HttpStatus.OK)
    public void getAccount(@PathVariable("address") String address) {

        testByKASService.getAccount(address);
    }

    @ApiOperation(
            value = "어떤 주소의 계정에서 발신된 트랜잭션의 개수 확인",
            notes = "getTransactionCount"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
    })
    @GetMapping("/accounts/{address}/transaction-count")
    @ResponseStatus(HttpStatus.OK)
    public void getTransactionCount(@PathVariable("address") String address) {

        testByKASService.getTransactionCount(address);
    }

    @ApiOperation(
            value = "입력으로 받은 주소에 해당하는 계정의 잔액 확인",
            notes = "getBalance"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
    })
    @GetMapping("/accounts/{address}/balance")
    @ResponseStatus(HttpStatus.OK)
    public void getBalance(@PathVariable("address") String address) {

        testByKASService.getBalance(address);
    }

}
