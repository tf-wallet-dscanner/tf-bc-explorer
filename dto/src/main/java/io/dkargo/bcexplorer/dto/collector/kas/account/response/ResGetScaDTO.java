package io.dkargo.bcexplorer.dto.collector.kas.account.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetScaDTO {

    @ApiModelProperty(value = "balance")
    private String balance;

    @ApiModelProperty(value = "humanReadable")
    private Boolean humanReadable;

    @ApiModelProperty(value = "nonce")
    private String nonce;

    @ApiModelProperty(value = "type")
    private String type;

    // maybe.. SCA 에만 존재
    @ApiModelProperty(value = "codeFormat")
    private String codeFormat;

    // maybe.. SCA 에만 존재
    @ApiModelProperty(value = "codeHash")
    private String codeHash;

    // maybe.. SCA 에만 존재
    @ApiModelProperty(value = "storageRoot")
    private String storageRoot;
}
