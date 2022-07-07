package io.dkargo.bcexplorer.dto.api.kas.transaction.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetTransactionListByBlockNumberDTO {

    @ApiModelProperty(value = "Page")
    private Integer page;

    @ApiModelProperty(value = "Size")
    private Integer size;

    @ApiModelProperty(value = "Total Page Count")
    private Integer totalPageCount;

    @ApiModelProperty(value = "Total Element Count")
    private Long totalElementCount;

    @ApiModelProperty(value = "List")
    private List<GetTransactionByBlockNumber> list;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class GetTransactionByBlockNumber {

        @ApiModelProperty(value = "TX HASH")
        private String txHash;

        @ApiModelProperty(value = "BLOCK #")
        private Long blockNumber;

        @ApiModelProperty(value = "CreateAt")
        private LocalDateTime createAt;

        @ApiModelProperty(value = "FROM")
        private String from;

        @ApiModelProperty(value = "TO")
        private String to;

        @ApiModelProperty(value = "METHOD")
        private String method;

        @ApiModelProperty(value = "TX TYPE")
        private String txType;

        @ApiModelProperty(value = "AMOUNT(KLAY)")
        private String amount;

        @ApiModelProperty(value = "TX FEE(KLAY)")
        private String txFee;
    }

    public ResGetTransactionListByBlockNumberDTO(Integer number, Integer size, Integer totalPageCount, Long totalElementCount, List<ResGetTransactionDTO> resGetTransactionDTOS) {

        this.page = number;
        this.size = size;
        this.totalPageCount = totalPageCount;
        this.totalElementCount = totalElementCount;

        List<GetTransactionByBlockNumber> getTransactionByBlockNumberList = new ArrayList<>();

        for(ResGetTransactionDTO resGetTransactionDTO : resGetTransactionDTOS) {

            GetTransactionByBlockNumber getTransactionByBlockNumber = GetTransactionByBlockNumber.builder()
                    .txHash(resGetTransactionDTO.getTransactionHash())
                    .blockNumber(resGetTransactionDTO.getOverview().getBlockNumber())
                    .createAt(resGetTransactionDTO.getOverview().getCreateAt())
                    .from(resGetTransactionDTO.getOverview().getFrom())
                    .to(resGetTransactionDTO.getOverview().getTo())
                    .method(resGetTransactionDTO.getOverview().getMethodSig())
                    .txType(resGetTransactionDTO.getOverview().getTxType())
                    .amount(resGetTransactionDTO.getOverview().getAmount())
                    .txFee(resGetTransactionDTO.getOverview().getTxFee())
                    .build();

            getTransactionByBlockNumberList.add(getTransactionByBlockNumber);
        }
        this.list = getTransactionByBlockNumberList;
    }
}
