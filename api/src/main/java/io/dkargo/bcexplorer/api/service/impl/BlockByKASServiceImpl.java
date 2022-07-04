package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.service.BlockByKASService;
import io.dkargo.bcexplorer.api.service.converter.BlockByKASConverter;
import io.dkargo.bcexplorer.api.service.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.entity.Block;
import io.dkargo.bcexplorer.domain.repository.BlockRepository;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListDTO;
import io.dkargo.bcexplorer.dto.domain.kas.block.response.ResBlockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockByKASServiceImpl implements BlockByKASService {

    private final BlockRepository blockRepository;

    @Override
    public ResGetBlockDTO getBlockByNumber(Long blockNumber) {

        Block block = blockRepository.findByResult_Number(CommonConverter.longToHex(blockNumber));

        ResBlockDTO resBlockDTO = BlockByKASConverter.of(block);

        return new ResGetBlockDTO(resBlockDTO);
    }

    @Override
    public ResGetBlockListDTO getBlockList() {

        ResGetBlockListDTO resGetBlockListDTO = ResGetBlockListDTO.builder().id("gg").build();

        return resGetBlockListDTO;
    }

}
