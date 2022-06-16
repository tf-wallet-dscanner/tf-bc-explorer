package io.dkargo.bcexplorer.dto.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResCreateTestDTO {

    @ApiModelProperty(value = "test ID")
    @NotNull
    private String id;
}
