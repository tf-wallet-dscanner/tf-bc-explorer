package io.dkargo.bcexplorer.dto.collector.kas.block.request;

import io.dkargo.bcexplorer.core.type.KASRequestType;
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

    @ApiModelProperty(value = "error")
    private Error error;

    @ApiModelProperty(value = "kasRequestType")
    private KASRequestType kasRequestType;

    @ApiModelProperty(value = "rawResponse")
    private String rawResponse;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Error {

        private Integer code;

        private String message;

        private String data;
    }
}
