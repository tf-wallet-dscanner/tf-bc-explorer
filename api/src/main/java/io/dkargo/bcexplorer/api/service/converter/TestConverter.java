package io.dkargo.bcexplorer.api.service.converter;

import io.dkargo.bcexplorer.domain.entity.Test;
import io.dkargo.bcexplorer.dto.api.request.ReqTestDTO;
import io.dkargo.bcexplorer.dto.api.response.ResTestDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConverter {

    public static Test of(ReqTestDTO reqTestDTO) {

        return Test.builder()
                .name(reqTestDTO.getName())
                .old(reqTestDTO.getOld())
                .etc(reqTestDTO.getEtc())
                .build();
    }

    public static ResTestDTO of(Test test) {

        return ResTestDTO.builder()
                .id(test.getId())
                .name(test.getName())
                .old(test.getOld())
                .etc(test.getEtc())
                .build();
    }
}
