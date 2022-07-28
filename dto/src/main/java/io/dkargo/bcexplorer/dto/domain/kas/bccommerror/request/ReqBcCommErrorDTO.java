package io.dkargo.bcexplorer.dto.domain.kas.bccommerror.request;

import io.dkargo.bcexplorer.core.type.CaverRequestType;
import io.dkargo.bcexplorer.core.type.KASRequestType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqBcCommErrorDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "error")
    private Error error;

    @ApiModelProperty(value = "caverRequestType")
    private CaverRequestType caverRequestType;

    @ApiModelProperty(value = "kasRequestType")
    private KASRequestType kasRequestType;

    @ApiModelProperty(value = "rawResponse")
    private String rawResponse;

    @ApiModelProperty(value = "serviceCode")
    private String serviceCode;

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
