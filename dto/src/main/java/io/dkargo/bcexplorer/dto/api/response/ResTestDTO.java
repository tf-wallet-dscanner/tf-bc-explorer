package io.dkargo.bcexplorer.dto.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResTestDTO {

    @ApiModelProperty(value="test response", required = true)
    private Long test;
}
