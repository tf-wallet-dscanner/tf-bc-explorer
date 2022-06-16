package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.TestService;
import io.dkargo.bcexplorer.dto.collector.response.ResTestDTO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @ApiOperation(
            value = "test",
            notes = "test"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "testValue", value = "test value", required = true, defaultValue = "test", dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "test", response = ResTestDTO.class)
    })
    @GetMapping("/createAccount")
    @ResponseStatus(HttpStatus.OK)
    public ResTestDTO createAccount(@RequestParam(value = "testValue", required = true) String testValue) {

        return testService.test(testValue);
    }
}