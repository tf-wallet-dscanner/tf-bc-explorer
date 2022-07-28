package io.dkargo.bcexplorer.collector.client;

import io.dkargo.bcexplorer.collector.client.config.IApiClient;
import io.dkargo.bcexplorer.collector.client.config.IApiUrl;
import io.dkargo.bcexplorer.dto.collector.kas.block.request.ReqCreateBlockChainInfoBySchedulerDTO;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResCreateBlockChainInfoDTO;
import io.dkargo.dkargohttpclient.DkargoHttpClientResponseException;

import java.io.IOException;

public interface IBcExplorerCollectorClient<API_URL extends IApiUrl> extends IApiClient<API_URL> {

    ResCreateBlockChainInfoDTO createBlockChainInfoByKas(ReqCreateBlockChainInfoBySchedulerDTO reqCreateBlockChainInfoBySchedulerDTO) throws IOException, DkargoHttpClientResponseException;
}
