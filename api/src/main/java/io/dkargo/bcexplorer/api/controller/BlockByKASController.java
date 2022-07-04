package io.dkargo.bcexplorer.api.controller;

import io.dkargo.bcexplorer.api.service.BlockByKASService;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListDTO;
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
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getBlockList", response = ResGetBlockListDTO.class)
    })
    @GetMapping("/blocks")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBlockListDTO getBlockList() {

        return blockByKASService.getBlockList();
    }
}
