package io.dkargo.bcexplorer.dto.collector.kas.block.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqBlockErrorDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "code")
    private Integer code;

    @ApiModelProperty(value = "message")
    private String message;

    @ApiModelProperty(value = "data")
    private String data;

    @ApiModelProperty(value = "rawResponse")
    private String rawResponse;
}
