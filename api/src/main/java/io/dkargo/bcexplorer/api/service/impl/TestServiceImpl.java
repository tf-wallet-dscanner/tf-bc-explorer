package io.dkargo.bcexplorer.api.service.impl;

import io.dkargo.bcexplorer.api.service.TestService;
import io.dkargo.bcexplorer.api.service.converter.TestConverter;
import io.dkargo.bcexplorer.core.error.DkargoException;
import io.dkargo.bcexplorer.core.error.ErrorCodeEnum;
import io.dkargo.bcexplorer.domain.entity.Block;
import io.dkargo.bcexplorer.domain.entity.Test;
import io.dkargo.bcexplorer.domain.repository.TestRepository;
import io.dkargo.bcexplorer.dto.api.request.ReqCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqDeleteTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqUpdateTestDTO;
import io.dkargo.bcexplorer.dto.api.response.*;
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

        // save vs insert
        // insert : index(_id)값을 명시하고 사용했을 때, 해당 _id 가 있으면 중복으로 에러가 난다.
        // save : index(_id)값을 명시하고 사용했을 때, 해당 _id 가 있으면 덮어쓰게되며, 존재하지 않다면 insert 기능을 이용하여 저장한다.
        // Test test = testRepository.save(TestConverter.of(reqTestDTO));
        Test test = testRepository.insert(TestConverter.of(reqTestDTO));

        return new ResCreateTestDTO(test.getId());
    }

    @Override
    public ResUpdateTestDTO updateTest(ReqUpdateTestDTO reqUpdateTestDTO) {

        Test test = Test.builder()
                .id(reqUpdateTestDTO.getId())
                .name(reqUpdateTestDTO.getName())
                .old(reqUpdateTestDTO.getOld())
                .etc(reqUpdateTestDTO.getEtc())
                .build();

        testRepository.save(test);

        return new ResUpdateTestDTO(test.getId());
    }

    @Override
    public ResDeleteTestDTO deleteTest(ReqDeleteTestDTO reqDeleteTestDTO) {

        Test test = testRepository.findById(reqDeleteTestDTO.getId()).orElseThrow(() -> new DkargoException(ErrorCodeEnum.INVALID_FORMAT));

        testRepository.delete(test);

        return new ResDeleteTestDTO(test.getId());
    }
}
