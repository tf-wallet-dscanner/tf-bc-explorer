package io.dkargo.bcexplorer.collector.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bcexplorer.collector.client.config.AConfigData;
import io.dkargo.bcexplorer.collector.client.config.IApiUrl;
import io.dkargo.dkargohttpclient.DkargoHttpClient;

import java.util.HashMap;
import java.util.Map;

public abstract class ABcExplorerCollectorClient<API_URL extends IApiUrl, CONFIG_DATA extends AConfigData> implements IBcExplorerCollectorClient<API_URL> {

    protected final DkargoHttpClient httpClient;

    protected final CONFIG_DATA configData;

    protected Map<String, String> headers = new HashMap<>();

    protected ABcExplorerCollectorClient(CONFIG_DATA configData) {
        this.httpClient = new DkargoHttpClient(new ObjectMapper());
        this.configData = configData;
    }

    protected ABcExplorerCollectorClient(CONFIG_DATA configData, ObjectMapper objectMapper) {
        this.httpClient = new DkargoHttpClient(objectMapper);
        this.configData = configData;
    }

    @Override
    public String getFullUrl(API_URL apiUrl) {
        return configData.getApiUrl() + apiUrl.getUrl();
    }
}
