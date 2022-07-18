package io.dkargo.bcexplorer.dto.collector.kas.account.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetEoaDTO {

    @ApiModelProperty(value = "balance")
    private String balance;

    @ApiModelProperty(value = "humanReadable")
    private Boolean humanReadable;

    @ApiModelProperty(value = "nonce")
    private String nonce;

    @ApiModelProperty(value = "type")
    private String type;
}
