package io.dkargo.bcexplorer.dto.api.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetBlockListDTO {

    @ApiModelProperty(value = "id")
    private String id;
}
