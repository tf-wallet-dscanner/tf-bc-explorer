package io.dkargo.bcexplorer.dto.api.kas.block.response;

import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.dto.domain.kas.block.response.ResBlockDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResGetBlockDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "Block Number")
    private Long blockNumber;

    @ApiModelProperty(value = "Overview")
    private Overview overview;

    @ApiModelProperty(value = "Committee")
    private Committee committee;

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Overview {

        @ApiModelProperty(value = "Timestamp")
        private Long timestamp;

        @ApiModelProperty(value = "CreateAt")
        private LocalDateTime createAt;

        @ApiModelProperty(value = "hash")
        private String hash;

        @ApiModelProperty(value = "Parent Hash")
        private String parentHash;

        @ApiModelProperty(value = "Total TXs")
        private Integer totalTXs;

        @ApiModelProperty(value = "Block Reward")
        private String blockReward;

        @ApiModelProperty(value = "Minted")
        private String minted;

        @ApiModelProperty(value = "TX Fee")
        private String txFee;

        @ApiModelProperty(value = "Block Size (bytes)")
        private Long blockSize;
    }

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Committee {

        @ApiModelProperty(value = "Block Proposer")
        private String blockProposer;

        @ApiModelProperty(value = "Validators")
        private List<String> validators;

        @ApiModelProperty(value = "Validator Count")
        private Integer validatorCount;
    }


    public ResGetBlockDTO(ResBlockDTO resBlockDTO) {

        Double txFee = Double.parseDouble(resBlockDTO.getResult().getBlockReward()) - 9.6d;


        Overview overview = Overview.builder()
                .timestamp(CommonConverter.hexToLong(resBlockDTO.getResult().getTimestamp()))
                .createAt(CommonConverter.stringToLocalDateTime(resBlockDTO.getCreateAt()))
                .hash(resBlockDTO.getResult().getHash())
                .parentHash(resBlockDTO.getResult().getParentHash())
                .totalTXs(resBlockDTO.getResult().getTransactionCount())
                .blockReward(resBlockDTO.getResult().getBlockReward())
                .minted("9.6")
                .txFee(CommonConverter.doubleToFormatString(txFee))
                .blockSize(CommonConverter.hexToLong(resBlockDTO.getResult().getSize()))
                .build();

        List<String> validators = new ArrayList<>();
        for(String validator : resBlockDTO.getResult().getCommittee()) {

            if(!resBlockDTO.getResult().getProposer().equals(validator)){
                validators.add(validator);
            }
        }

        Committee committee = Committee.builder()
                .blockProposer(resBlockDTO.getResult().getProposer())
                .validators(validators)
                .validatorCount(resBlockDTO.getResult().getCommittee().size() - 1)
                .build();


        this.id = resBlockDTO.getId();
        this.blockNumber = CommonConverter.hexToLong(resBlockDTO.getResult().getNumber());
        this.overview = overview;
        this.committee = committee;
    }
}
