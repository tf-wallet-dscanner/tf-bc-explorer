package io.dkargo.bcexplorer.collector.client;

import io.dkargo.bcexplorer.collector.client.config.IApiClient;
import io.dkargo.bcexplorer.collector.client.config.IApiUrl;
import io.dkargo.bcexplorer.dto.collector.kas.block.response.ResCreateBlockDTO;
import io.dkargo.dkargohttpclient.DkargoHttpClientResponseException;

import java.io.IOException;
import java.util.zip.DataFormatException;

public interface IBcExplorerCollectorClient<API_URL extends IApiUrl> extends IApiClient<API_URL> {

    ResCreateBlockDTO createBlockWithTransaction() throws IOException, DkargoHttpClientResponseException;
}
