package io.dkargo.bcexplorer.dto.api.kas.transaction.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetTransactionListByBlockNumberDTO {

    @ApiModelProperty(value = "id")
    private String id;
//    @ApiModelProperty(value = "Page")
//    private Integer page;
//
//    @ApiModelProperty(value = "Size")
//    private Integer size;
//
//    @ApiModelProperty(value = "Total Page Count")
//    private Integer totalPageCount;
//
//    @ApiModelProperty(value = "Total Element Count")
//    private Long totalElementCount;
//
//    @ApiModelProperty(value = "List")
//    private List<ResGetTransactionDTO> list;
//
//    public ResGetTransactionListByBlockNumberDTO() {
//
//        this.page
//        this.size
//        this.totalPageCount
//        this.totalElementCount
//        this.list
//    }
}
