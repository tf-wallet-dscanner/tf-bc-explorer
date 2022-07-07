package io.dkargo.bcexplorer.dto.api.kas.transaction.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetTransactionListInDashboardDTO {

    @ApiModelProperty(value = "Page")
    private Integer page;

    @ApiModelProperty(value = "Size")
    private Integer size;

    @ApiModelProperty(value = "Total Page Count")
    private Integer totalPageCount;

    @ApiModelProperty(value = "Total Element Count")
    private Long totalElementCount;

    @ApiModelProperty(value = "List")
    private List<GetTransactionInDashboard> list;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class GetTransactionInDashboard {

        @ApiModelProperty(value = "TX HASH")
        private String txHash;

        @ApiModelProperty(value = "CreateAt")
        private LocalDateTime createAt;

        @ApiModelProperty(value = "FROM")
        private String from;

        @ApiModelProperty(value = "TO")
        private String to;
    }

    public ResGetTransactionListInDashboardDTO(Integer number, Integer size, Integer totalPageCount, Long totalElementCount, List<ResGetTransactionDTO> resGetTransactionDTOS) {

        this.page = number;
        this.size = size;
        this.totalPageCount = totalPageCount;
        this.totalElementCount = totalElementCount;

        List<GetTransactionInDashboard> getTransactionInDashboardList = new ArrayList<>();

        for(ResGetTransactionDTO resGetTransactionDTO : resGetTransactionDTOS) {

            GetTransactionInDashboard getTransactionInDashboard = GetTransactionInDashboard.builder()
                    .txHash(resGetTransactionDTO.getTransactionHash())
                    .createAt(resGetTransactionDTO.getOverview().getCreateAt())
                    .from(resGetTransactionDTO.getOverview().getFrom())
                    .to(resGetTransactionDTO.getOverview().getTo())
                    .build();

            getTransactionInDashboardList.add(getTransactionInDashboard);
        }

        this.list = getTransactionInDashboardList;
    }
}
