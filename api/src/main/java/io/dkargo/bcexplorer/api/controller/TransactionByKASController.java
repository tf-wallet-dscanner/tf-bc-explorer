package io.dkargo.bcexplorer.api.controller;

import io.dkargo.bcexplorer.api.service.TransactionByKASService;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListByBlockNumberDTO;
import io.dkargo.bcexplorer.dto.api.kas.transaction.response.ResGetTransactionListDTO;
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
            value = "단건 트랜잭션 조회",
            notes = "getTransactionByHash"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transactionHash", value = "트랜잭션 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getTransactionByHash", response = ResGetTransactionDTO.class)
    })
    @GetMapping("/transactions/hash/{transactionHash}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetTransactionDTO getTransactionByHash(@PathVariable("transactionHash") String transactionHash) {

        return transactionByKASService.getTransactionByHash(transactionHash);
    }

    @ApiOperation(
            value = "다건 트랜잭션 조회",
            notes = "getTransactionList"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getTransactionList", response = ResGetTransactionListDTO.class)
    })
    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public ResGetTransactionListDTO getTransactionList() {

        return transactionByKASService.getTransactionList();
    }

    @ApiOperation(
            value = "하나의 블록에 존재하는 다건 트랜잭션 조회",
            notes = "getTransactionListByBlockNumber"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockNumber", value = "블록 번호", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = true, defaultValue = "0", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = true, defaultValue = "20", dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getTransactionListByBlockNumber", response = ResGetTransactionListByBlockNumberDTO.class)
    })
    @GetMapping("/transactions/block-number/{blockNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetTransactionListByBlockNumberDTO getTransactionListByBlockNumber(@PathVariable("blockNumber") Long blockNumber,
                                                                                 @RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
                                                                                 @RequestParam(value = "size", required = true, defaultValue = "20") Integer size) {

        return transactionByKASService.getTransactionListByBlockNumber(blockNumber, page, size);
    }
}
