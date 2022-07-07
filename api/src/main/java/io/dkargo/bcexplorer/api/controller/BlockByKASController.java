package io.dkargo.bcexplorer.api.controller;

import io.dkargo.bcexplorer.api.service.BlockByKASService;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListInDashboardDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetLatestBlockNumberDTO;
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
            value = "단건 블록 조회",
            notes = "getBlockByNumber"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blockNumber", value = "블록 번호", required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getBlockByNumber", response = ResGetBlockDTO.class)
    })
    @GetMapping("/blocks/number/{blockNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockDTO getBlockByNumber(@PathVariable("blockNumber") Long blockNumber) {

        return blockByKASService.getBlockByNumber(blockNumber);
    }

    @ApiOperation(
            value = "다건 블록 조회",
            notes = "getBlockList"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = true, defaultValue = "0", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = true, defaultValue = "20", dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getBlockList", response = ResGetBlockListDTO.class)
    })
    @GetMapping("/blocks")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockListDTO getBlockList(@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", required = true, defaultValue = "20") Integer size) {

        return blockByKASService.getBlockList(page, size);
    }

    @ApiOperation(
            value = "[대시보드] 최신 블록 번호 조회",
            notes = "getLatestBlockNumber"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getLatestBlockNumber", response = ResGetLatestBlockNumberDTO.class)
    })
    @GetMapping("/blocks/dashboard/latest-block-number")
    @ResponseStatus(HttpStatus.OK)
    public ResGetLatestBlockNumberDTO getLatestBlockNumber() {

        return blockByKASService.getLatestBlockNumber();
    }

    @ApiOperation(
            value = "[대시보드] 다건 블록 조회",
            notes = "getBlockListInDashboard"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = true, defaultValue = "0", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", required = true, defaultValue = "20", dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getBlockList", response = ResGetBlockListInDashboardDTO.class)
    })
    @GetMapping("/blocks/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockListInDashboardDTO getBlockListInDashboard(@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "size", required = true, defaultValue = "20") Integer size) {

        return blockByKASService.getBlockListInDashboard(page, size);
    }
}
