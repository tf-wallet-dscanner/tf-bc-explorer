package io.dkargo.bcexplorer.dto.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReqUpdateTestDTO {

    @ApiModelProperty(value = "test ID", required = true)
    @NotNull
    private String id;

    @ApiModelProperty(value = "이름", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "나이", required = true)
    @NotNull
    private int old;

    @ApiModelProperty(value = "등등")
    private String etc;
}
