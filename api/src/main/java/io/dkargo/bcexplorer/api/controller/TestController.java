package io.dkargo.bcexplorer.api.controller;

import io.dkargo.bcexplorer.api.service.TestService;
import io.dkargo.bcexplorer.dto.api.request.ReqCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.response.ResCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.response.ResGetTestListDTO;
import io.dkargo.bcexplorer.dto.api.response.ResTestDTO;
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

//    @ApiOperation(
//            value = "test",
//            notes = "test"
//    )
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "testValue", value = "test value", required = true, defaultValue = "test", dataType = "string", paramType = "query")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "test", response = ResTestDTO.class)
//    })
//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public ResTestDTO test(@RequestParam(value = "testValue", required = true) String testValue) {
//
//        return testService.test(testValue);
//    }

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
            notes = "test collection 에 Document 생성"
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

    /** TODO
     *  1. List 필터 조회
     *  3. 삭제
     *  4. 수정
     */
}
