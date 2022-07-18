package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.AccountByKASService;
import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account-by-kas")
public class AccountByKASController {

    private final AccountByKASService accountByKASService;

    @ApiOperation(
            value = "주소를 통해 계정 정보 조회 (Klaytn에는 스마트 컨트랙트 계쩡과 외부 소유 계정(EOA)이 있다.)",
            notes = "getAccountByAddress"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "주소", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getAccountByAddress", response = ResGetAccountDTO.class)
    })
    @GetMapping("/accounts/address/{address}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetAccountDTO getAccountByAddress(@PathVariable("address") String address) {

        return accountByKASService.getAccountByAddress(address);
    }
}
