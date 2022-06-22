package io.dkargo.bcexplorer.dto.collector.kas.block.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqCreateBlockByHashDTO {

    @ApiModelProperty(value = "블록 해쉬", required = true)
    @NotNull
    private String blockHash;
}
