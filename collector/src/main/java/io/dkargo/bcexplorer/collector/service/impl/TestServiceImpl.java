package io.dkargo.bcexplorer.collector.service.impl;

import io.dkargo.bcexplorer.collector.service.TestService;
import io.dkargo.bcexplorer.dto.collector.response.ResTestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    @Override
    public ResTestDTO test(String testValue) {

        log.info("test value : {}", testValue);

        ResTestDTO resTestDTO = ResTestDTO.builder().test(920723L).build();

        return resTestDTO;
    }
}
