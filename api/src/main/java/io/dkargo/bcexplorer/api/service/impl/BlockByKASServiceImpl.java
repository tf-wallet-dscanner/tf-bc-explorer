package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.service.BlockByKASService;
import io.dkargo.bcexplorer.api.service.converter.BlockByKASConverter;
import io.dkargo.bcexplorer.core.converter.CommonConverter;
import io.dkargo.bcexplorer.domain.entity.Block;
import io.dkargo.bcexplorer.domain.repository.BlockRepository;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockDTO;
import io.dkargo.bcexplorer.dto.api.kas.block.response.ResGetBlockListDTO;
import io.dkargo.bcexplorer.dto.domain.kas.block.response.ResBlockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResGetBlockListDTO getBlockList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<Block> blockPage =  blockRepository.findAllBy(pageable);

        List<Block> blocks = blockPage.getContent();

        List<ResGetBlockDTO> resGetBlockDTOS = new ArrayList<>();
        for(Block block : blocks) {

            ResGetBlockDTO resGetBlockDTO = new ResGetBlockDTO(BlockByKASConverter.of(block));
            resGetBlockDTOS.add(resGetBlockDTO);
        }

        return new ResGetBlockListDTO(blockPage.getNumber(), blockPage.getSize(), blockPage.getTotalPages(), blockPage.getTotalElements(), resGetBlockDTOS);
    }

}
