package io.dkargo.bcexplorer.dto.domain.kas.bccommerror.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResBcCommErrorDTO {

    @ApiModelProperty(value = "id")
    @NotNull
    private String id;

    @ApiModelProperty(value = "errorId")
    private Long errorId;

    @ApiModelProperty(value = "jsonrpc")
    private String jsonrpc;

    @ApiModelProperty(value = "error")
    private Error error;

    @ApiModelProperty(value = "caverRequestType")
    private String caverRequestType;

    @ApiModelProperty(value = "kasRequestType")
    private String kasRequestType;

    @ApiModelProperty(value = "rawResponse")
    private String rawResponse;

    @ApiModelProperty(value = "createAt")
    private String createAt;

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
