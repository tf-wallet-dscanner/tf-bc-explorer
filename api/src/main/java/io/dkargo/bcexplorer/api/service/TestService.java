package io.dkargo.bcexplorer.api.service;

import io.dkargo.bcexplorer.dto.api.request.ReqCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.response.ResCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.response.ResGetTestListDTO;
import io.dkargo.bcexplorer.dto.api.response.ResTestDTO;

public interface TestService {

    ResTestDTO test(String testValue);

    /**
     * test collection 에 있는 데이터 모두 조회
     * @return
     */
    ResGetTestListDTO getTestListByAll();

    /**
     * test collection 에 있는 데이터 중 검색 조건에 맞게 조회
     * @return
     */
    ResGetTestListDTO getTestListByFilter(String name);

    /**
     * test collection 에 Document 생성
     * @param reqCreateTestDTO
     * @return
     */
    ResCreateTestDTO createTest(ReqCreateTestDTO reqCreateTestDTO);
}
