package io.dkargo.bcexplorer.dto.api.kas.transaction.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetTransactionListByBlockNumberDTO {

    @ApiModelProperty(value = "id")
    private String id;
}
