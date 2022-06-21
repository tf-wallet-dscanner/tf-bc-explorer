package io.dkargo.bcexplorer.dto.collector.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetBlockTransactionCountDTO {

    @ApiModelProperty(value = "트랜잭션 개수")
    @NotNull
    private Long transactionCount;
}
