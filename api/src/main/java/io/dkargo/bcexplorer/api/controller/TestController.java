package io.dkargo.bcexplorer.api.controller;

import io.dkargo.bcexplorer.api.service.TestService;
import io.dkargo.bcexplorer.dto.api.request.ReqCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqDeleteTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqUpdateTestDTO;
import io.dkargo.bcexplorer.dto.api.response.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @ApiOperation(
            value = "getTestListByAll",
            notes = "test collection 에 있는 데이터 모두 조회"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getTestListByAll", response = ResGetTestListDTO.class)
    })
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResGetTestListDTO getTestListByAll( ) {

        return testService.getTestListByAll();
    }

    @ApiOperation(
            value = "getTestListByFilter",
            notes = "test collection 에 있는 데이터 중 검색 조건에 맞는 데이터 모두 조회 (name like)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "이름", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResGetTestListDTO getTestListByFilter(@RequestParam(value = "name", required = true) String name) {

        return testService.getTestListByFilter(name);
    }

    @ApiOperation(
            value = "createTest",
            notes = "test collection 에 document 생성"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "createTest", response = ResCreateTestDTO.class)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResCreateTestDTO createTest(@Validated @RequestBody ReqCreateTestDTO reqCreateTestDTO) {

        return testService.createTest(reqCreateTestDTO);
    }

    @ApiOperation(
            value = "updateTest",
            notes = "test collection 의 특정 document 수정"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "updateTest", response = ResUpdateTestDTO.class)
    })
    @PatchMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResUpdateTestDTO updateTest(@Validated @RequestBody ReqUpdateTestDTO reqUpdateTestDTO) {

        return testService.updateTest(reqUpdateTestDTO);
    }

    @ApiOperation(
            value = "deleteTest",
            notes = "test collection 의 특정 document 삭제"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "deleteTest", response = ResDeleteTestDTO.class)
    })
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResDeleteTestDTO deleteTest(@Validated @RequestBody ReqDeleteTestDTO reqDeleteTestDTO) {

        return testService.deleteTest(reqDeleteTestDTO);
    }

}
