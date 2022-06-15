package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.service.TestService;
import io.dkargo.bcexplorer.dto.api.response.ResTestDTO;
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

        ResTestDTO resTestDTO = ResTestDTO.builder().test(1004L).build();

        return resTestDTO;
    }
}
