package io.dkargo.bcexplorer.dto.domain.kas.account.request;

import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqEoaDTO {

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "createAt")
    private String createAt;

    @ApiModelProperty(value = "resultByGetAccount")
    private ResGetAccountDTO.Result resultByGetAccount;
}
