package io.dkargo.bcexplorer.dto.api.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetBlockListDTO {


    @ApiModelProperty(value = "Page")
    private Integer page;

    @ApiModelProperty(value = "Size")
    private Integer size;

    @ApiModelProperty(value = "Total Page Count")
    private Integer totalPageCount;

    @ApiModelProperty(value = "Total Element Count")
    private Long totalElementCount;

    @ApiModelProperty(value = "List")
    private List<ResGetBlockDTO> list;

    public ResGetBlockListDTO(Integer number, Integer size, Integer totalPageCount, Long totalElementCount, List<ResGetBlockDTO> resGetBlockDTOS) {

        this.page = number;
        this.size = size;
        this.totalPageCount = totalPageCount;
        this.totalElementCount = totalElementCount;
        this.list = resGetBlockDTOS;
    }
}
