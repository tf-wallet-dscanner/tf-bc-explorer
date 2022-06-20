package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.dto.collector.response.ResGetLatestBlockNumberDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block-by-kas")
public class BlockByKASController {

    private final BlockByKASService blockByKASService;

    @ApiOperation(
            value = "getLatestBlockNumber",
            notes = "최신의 블록 번호 조회"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getLatestBlockNumber", response = ResGetLatestBlockNumberDTO.class)
    })
    @GetMapping("/blocks")
    @ResponseStatus(HttpStatus.OK)
    public ResGetLatestBlockNumberDTO getLatestBlockNumber() {

        return blockByKASService.getLatestBlockNumber();
    }

    @ApiOperation(
            value = "getBlockByBlockNumber",
            notes = "블록 번호를 통해 블록 정보 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockNumber", value = "블록 번호", required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
    //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/{blockNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void getBlockByBlockNumber(@PathVariable("blockNumber") Long blockNumber) {

        blockByKASService.getBlockByBlockNumber(blockNumber);
    }

    @ApiOperation(
            value = "getBlockReceiptByBlockHash",
            notes = "블록 해쉬를 통해 블록 정보 조회"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockHash", value = "블록 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/receipts/{blockHash}")
    @ResponseStatus(HttpStatus.OK)
    public void getBlockReceiptByBlockHash(@PathVariable("blockHash") String blockHash) {

        blockByKASService.getBlockReceiptByBlockHash(blockHash);
    }
}
