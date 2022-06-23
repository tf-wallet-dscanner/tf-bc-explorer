package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.BlockByKASService;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByHashDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockByNumberDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/block-by-kas")
public class BlockByKASController {

    private final BlockByKASService blockByKASService;

    @ApiOperation(
            value = "최신의 블록 번호 조회",
            notes = "getLatestBlockNumber"
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
            value = "블록 번호를 통해 블록 정보 조회",
            notes = "getBlockByBlockNumber"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockNumber", value = "블록 번호", required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
    //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/number/{blockNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockDTO getBlockByNumber(@PathVariable("blockNumber") Long blockNumber) {

        return blockByKASService.getBlockByNumber(blockNumber);
    }

    @ApiOperation(
            value = "블록 해쉬를 통해 블록 정보 조회",
            notes = "getBlockByHash"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockHash", value = "블록 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/hash/{blockHash}")
    @ResponseStatus(HttpStatus.OK)
    public void getBlockByHash(@PathVariable("blockHash") String blockHash) {

        blockByKASService.getBlockByHash(blockHash);
    }

    @ApiOperation(
            value = "블록 해쉬를 통해 블록 receipt 정보 조회",
            notes = "getBlockReceiptByBlockHash"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockHash", value = "블록 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/receipt/hash/{blockHash}")
    @ResponseStatus(HttpStatus.OK)
    public void getBlockReceiptByHash(@PathVariable("blockHash") String blockHash) {

        blockByKASService.getBlockReceiptByHash(blockHash);
    }

    @ApiOperation(
            value = "블록 번호를 통해 블록 정보와 Consensus 정보 조회",
            notes = "getBlockWithConsensusInfoByNumber"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockNumber", value = "블록 번호", required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getBlockWithConsensusInfoByNumber", response = ResGetBlockWithConsensusInfoDTO.class)
    })
    @GetMapping("/blocks/consensus-with/number/{blockNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByNumber(@PathVariable("blockNumber") Long blockNumber) {

        return blockByKASService.getBlockWithConsensusInfoByNumber(blockNumber);
    }

    @ApiOperation(
            value = "블록 해쉬를 통해 블록 정보와 Consensus 정보 조회",
            notes = "getBlockWithConsensusInfoByHash"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockHash", value = "블록 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/consensus-with/hash/{blockHash}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockWithConsensusInfoDTO getBlockWithConsensusInfoByHash(@PathVariable("blockHash") String blockHash) {

        return blockByKASService.getBlockWithConsensusInfoByHash(blockHash);
    }

    @ApiOperation(
            value = "블록 번호를 통해 블록의 트랜잭션 개수 조회",
            notes = "getBlockTransactionCountByNumber"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockNumber", value = "블록 번호", required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/transaction/number/{blockNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockTransactionCountDTO getBlockTransactionCountByNumber(@PathVariable("blockNumber") Long blockNumber) {

        return blockByKASService.getBlockTransactionCountByNumber(blockNumber);
    }

    @ApiOperation(
            value = "블록 해쉬를 통해 블록의 트랜잭션 개수 조회",
            notes = "getBlockTransactionCountByHash"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockHash", value = "블록 해쉬", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/blocks/transaction/hash/{blockHash}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockTransactionCountDTO getBlockTransactionCountByHash(@PathVariable("blockHash") String blockHash) {

        return blockByKASService.getBlockTransactionCountByHash(blockHash);
    }

    @ApiOperation(
            value = "블록 번호를 통해 블록 정보와 트랜잭션 정보 생성",
            notes = "createBlockWithTransactionByNumber"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "createBlockWithTransactionByNumber", response = ResCreateBlockDTO.class)
    })
    @PostMapping("/blocks/number")
    @ResponseStatus(HttpStatus.CREATED)
    public ResCreateBlockDTO createBlockWithTransactionByNumber(@Validated @RequestBody ReqCreateBlockByNumberDTO reqCreateBlockByNumberDTO) {

        return blockByKASService.createBlockWithTransactionByNumber(reqCreateBlockByNumberDTO);
    }

    @ApiOperation(
            value = "블록 해쉬를 통해 블록 정보와 트랜잭션 정보 생성",
            notes = "createBlockWithTransactionByHash"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "createBlockWithTransactionByHash", response = ResCreateBlockDTO.class)
    })
    @PostMapping("/blocks/hash")
    @ResponseStatus(HttpStatus.CREATED)
    public ResCreateBlockDTO createBlockWithTransactionByHash(@Validated @RequestBody ReqCreateBlockByHashDTO reqCreateBlockByHashDTO) {

        return blockByKASService.createBlockWithTransactionByHash(reqCreateBlockByHashDTO);
    }
}