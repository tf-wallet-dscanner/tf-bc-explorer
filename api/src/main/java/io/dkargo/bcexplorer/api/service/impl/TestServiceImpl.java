package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.domain.entity.Test;
import io.dkargo.bcexplorer.api.domain.repository.TestRepository;
import io.dkargo.bcexplorer.api.service.TestService;
import io.dkargo.bcexplorer.api.service.converter.TestConverter;
import io.dkargo.bcexplorer.dto.api.response.ResGetTestDTO;
import io.dkargo.bcexplorer.dto.api.response.ResGetTestListDTO;
import io.dkargo.bcexplorer.dto.api.response.ResTestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
//    private final HoRepository hoRepository;

    @Override
    public ResTestDTO test(String testValue) {

        log.info("test value : {}", testValue);

        ResTestDTO resTestDTO = ResTestDTO.builder()
                .name("손오공")
                .old(10000)
                .etc("강함")
                .build();

        return resTestDTO;
    }

    /**
     * test collection 에 있는 데이터 모두 조회
     * @return
     */
    @Override
    public ResGetTestListDTO getTestListByAll() {

        List<Test> tests = testRepository.findAll();

        List<ResGetTestDTO> resGetTestDTOS = new ArrayList<>();

        for(Test test : tests) {

            ResTestDTO resTestDTO = TestConverter.of(test);
            log.info("name : {}" , test.getName());

            resGetTestDTOS.add(new ResGetTestDTO(resTestDTO));
        }


        return new ResGetTestListDTO(resGetTestDTOS);
    }
}
