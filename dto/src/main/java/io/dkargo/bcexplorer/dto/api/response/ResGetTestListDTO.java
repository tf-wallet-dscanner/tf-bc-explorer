package io.dkargo.bcexplorer.dto.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetTestListDTO {

    @ApiModelProperty(value = "Test List")
    @NotNull
    private List<ResGetTestDTO> resGetTestDTOS;
}
