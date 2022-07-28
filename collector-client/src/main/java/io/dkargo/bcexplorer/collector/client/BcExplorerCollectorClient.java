package io.dkargo.bcexplorer.collector.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoBySchedulerDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResCreateBlockChainInfoDTO;
import io.dkargo.dkargohttpclient.DkargoHttpClientResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class BcExplorerCollectorClient extends ABcExplorerCollectorClient<BcExplorerCollectorUrl, BcExplorerCollectorConfigData> {

    private static Logger log = null;

    private final DateTimeFormatter DATA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BcExplorerCollectorClient(BcExplorerCollectorConfigData configData) { super(configData); }

    public BcExplorerCollectorClient(BcExplorerCollectorConfigData configData, ObjectMapper objectMapper) {
        super(configData, objectMapper);
        log = LoggerFactory.getLogger(BcExplorerCollectorClient.class);
    }

    public BcExplorerCollectorClient(BcExplorerCollectorConfigData configData, ObjectMapper objectMapper, Logger log) {
        super(configData, objectMapper);
        this.log = log;
    }

    /**
     * 블록 정보와 트랜잭션 정보 및 계정 정보 생성
     *
     * @param reqCreateBlockChainInfoBySchedulerDTO
     * @return
     * @throws IOException
     * @throws DkargoHttpClientResponseException
     */
    @Override
    public ResCreateBlockChainInfoDTO createBlockChainInfoByKas(ReqCreateBlockChainInfoBySchedulerDTO reqCreateBlockChainInfoBySchedulerDTO) throws IOException, DkargoHttpClientResponseException {

        String url = getFullUrl(BcExplorerCollectorUrl.CREATE_BLOCK_CHAIN_INFO_BY_KAS);

        return httpClient.post(url, ResCreateBlockChainInfoDTO.class, reqCreateBlockChainInfoBySchedulerDTO, headers);
    }
}
