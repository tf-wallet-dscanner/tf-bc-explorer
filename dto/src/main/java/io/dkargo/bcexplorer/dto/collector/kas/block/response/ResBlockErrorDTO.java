package io.dkargo.bcexplorer.dto.collector.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResBlockErrorDTO {

    @ApiModelProperty(value = "id")
    @NotNull
    private String id;

    @ApiModelProperty(value = "blockId")
    private Long blockId;

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
