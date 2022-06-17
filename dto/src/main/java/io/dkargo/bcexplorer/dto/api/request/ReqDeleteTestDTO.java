package io.dkargo.bcexplorer.dto.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqDeleteTestDTO {

    @ApiModelProperty(value = "test ID", required = true)
    @NotNull
    private String id;
}
