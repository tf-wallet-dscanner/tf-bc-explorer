package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.domain.entity.Test;
import io.dkargo.bcexplorer.api.domain.repository.TestRepository;
import io.dkargo.bcexplorer.api.service.TestService;
import io.dkargo.bcexplorer.api.service.converter.TestConverter;
import io.dkargo.bcexplorer.dto.api.request.ReqCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqTestDTO;
import io.dkargo.bcexplorer.dto.api.response.ResCreateTestDTO;
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

    @Override
    public ResGetTestListDTO getTestListByAll() {

        List<Test> tests = testRepository.findAll();

        List<ResGetTestDTO> resGetTestDTOS = new ArrayList<>();

        for(Test test : tests) {

            ResTestDTO resTestDTO = TestConverter.of(test);
            resGetTestDTOS.add(new ResGetTestDTO(resTestDTO));
        }

        return new ResGetTestListDTO(resGetTestDTOS);
    }

    @Override
    public ResGetTestListDTO getTestListByFilter(String name) {

        List<Test> tests = testRepository.findAllByNameRegex(name);

        List<ResGetTestDTO> resGetTestDTOS = new ArrayList<>();

        for (Test test : tests) {

            ResTestDTO resTestDTO = TestConverter.of(test);
            resGetTestDTOS.add(new ResGetTestDTO(resTestDTO));
        }

        return new ResGetTestListDTO(resGetTestDTOS);
    }

    @Override
    public ResCreateTestDTO createTest(ReqCreateTestDTO reqCreateTestDTO) {

        ReqTestDTO reqTestDTO = ReqTestDTO.builder()
                .name(reqCreateTestDTO.getName())
                .old(reqCreateTestDTO.getOld())
                .etc(reqCreateTestDTO.getEtc())
                .build();

        Test test = testRepository.save(TestConverter.of(reqTestDTO));

        return new ResCreateTestDTO(test.getId());
    }
}
