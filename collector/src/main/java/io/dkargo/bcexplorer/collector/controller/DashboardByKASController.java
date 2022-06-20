package io.dkargo.bcexplorer.collector.controller;

import io.dkargo.bcexplorer.collector.service.DashboardByKASService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard-by-kas")
public class DashboardByKASController {

    private final DashboardByKASService dashboardByKASService;

    @ApiOperation(
            value = "getRecentDashboardInfo",
            notes = "최근 대시보드 정보 조회"
    )
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            //        @ApiResponse(code = 200, message = "getTestListByFilter", response = ResGetTestListDTO.class)
    })
    @GetMapping("/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public void getRecentDashboardInfo() {

        dashboardByKASService.getRecentDashboardInfo();
    }
}
