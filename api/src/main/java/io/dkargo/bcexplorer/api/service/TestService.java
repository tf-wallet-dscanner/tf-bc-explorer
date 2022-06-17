package io.dkargo.bcexplorer.api.service;

import io.dkargo.bcexplorer.dto.api.request.ReqCreateTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqDeleteTestDTO;
import io.dkargo.bcexplorer.dto.api.request.ReqUpdateTestDTO;
import io.dkargo.bcexplorer.dto.api.response.*;

public interface TestService {

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
     * test collection 에 document 생성
     * @param reqCreateTestDTO
     * @return
     */
    ResCreateTestDTO createTest(ReqCreateTestDTO reqCreateTestDTO);

    /**
     * test collection 의 특정 document 수정
     * @param reqUpdateTestDTO
     * @return
     */
    ResUpdateTestDTO updateTest(ReqUpdateTestDTO reqUpdateTestDTO);

    /**
     * test collection 의 특정 document 삭제
     * @param reqDeleteTestDTO
     * @return
     */
    ResDeleteTestDTO deleteTest(ReqDeleteTestDTO reqDeleteTestDTO);
}
