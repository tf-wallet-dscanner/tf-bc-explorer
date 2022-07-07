package io.dkargo.bcexplorer.dto.api.kas.block.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetBlockListInDashboardDTO {

    @ApiModelProperty(value = "Page")
    private Integer page;

    @ApiModelProperty(value = "Size")
    private Integer size;

    @ApiModelProperty(value = "Total Page Count")
    private Integer totalPageCount;

    @ApiModelProperty(value = "Total Element Count")
    private Long totalElementCount;

    @ApiModelProperty(value = "List")
    private List<GetBlockInDashboard> list;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class GetBlockInDashboard {

        @ApiModelProperty(value = "BLOCK #")
        private Long blockNumber;

        @ApiModelProperty(value = "CreateAt")
        private LocalDateTime createAt;

        @ApiModelProperty(value = "TOTAL TXS")
        private Integer totalTXs;

        @ApiModelProperty(value = "BLOCK PROPOSER")
        private String blockProposer;

        @ApiModelProperty(value = "REQARD(KLAY)")
        private String reward;
    }

    public ResGetBlockListInDashboardDTO(Integer number, Integer size, Integer totalPageCount, Long totalElementCount, List<ResGetBlockDTO> resGetBlockDTOS) {

        this.page = number;
        this.size = size;
        this.totalPageCount = totalPageCount;
        this.totalElementCount = totalElementCount;

        List<GetBlockInDashboard> getBlockInDashboardList = new ArrayList<>();

        for(ResGetBlockDTO resGetBlockDTO : resGetBlockDTOS) {

            GetBlockInDashboard getBlockInDashboard = GetBlockInDashboard.builder()
                    .blockNumber(resGetBlockDTO.getBlockNumber())
                    .createAt(resGetBlockDTO.getOverview().getCreateAt())
                    .totalTXs(resGetBlockDTO.getOverview().getTotalTXs())
                    .blockProposer(resGetBlockDTO.getCommittee().getBlockProposer())
                    .reward(resGetBlockDTO.getOverview().getBlockReward())
                    .build();

            getBlockInDashboardList.add(getBlockInDashboard);
        }
        this.list = getBlockInDashboardList;
    }
}
