package io.dkargo.bcexplorer.dto.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResTestDTO {

    @ApiModelProperty(value = "person ID")
    @NotNull
    private String id;

    @ApiModelProperty(value = "이름")
    @NotNull
    private String name;

    @ApiModelProperty(value = "나이")
    @NotNull
    private int old;

    @ApiModelProperty(value = "등등")
    private String etc;
}
