package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.TransactionByKASService;
import io.dkargo.bcexplorer.dto.collector.kas.transaction.response.ResGetTransactionReceiptByHashDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction-by-kas")
public class TransactionByKASController {

    private final TransactionByKASService transactionByKASService;

    @ApiOperation(
            value = "트랜잭션의 해쉬를 통해 트랜잭션 정보 조회",
            notes = "getTransactionByHash"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transactionHash", value = "트랜잭션 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/transactions/hash/{transactionHash}")
    @ResponseStatus(HttpStatus.OK)
    public void getTransactionByHash(@PathVariable("transactionHash") String transactionHash) {

        transactionByKASService.getTransactionByHash(transactionHash);
    }

    @ApiOperation(
            value = "트랜잭션의 해쉬를 통해 트랜잭션 receipt 정보 조회",
            notes = "getTransactionReceiptByHash"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transactionHash", value = "트랜잭션 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
                    @ApiResponse(code = 200, message = "getTransactionReceiptByHash", response = ResGetTransactionReceiptByHashDTO.class)
    })
    @GetMapping("/transactions/receipt/hash/{transactionHash}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetTransactionReceiptByHashDTO getTransactionReceiptByHash(@PathVariable("transactionHash") String transactionHash) {

        return transactionByKASService.getTransactionReceiptByHash(transactionHash);
    }
}
