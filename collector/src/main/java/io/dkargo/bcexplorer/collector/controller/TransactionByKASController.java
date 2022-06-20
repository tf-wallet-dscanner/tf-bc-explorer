package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.TransactionByKASService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction-by-kas")
public class TransactionByKASController {

    private final TransactionByKASService transactionByKASService;

    @ApiOperation(
            value = "getTransactionByHash",
            notes = "트랜잭션의 해쉬를 통해 트랜잭션 정보 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hash", value = "트랜잭션 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/transactions/{hash}")
    @ResponseStatus(HttpStatus.OK)
    public void getTransactionByHash(@PathVariable("hash") String hash) {

        transactionByKASService.getTransactionByHash(hash);
    }
}
