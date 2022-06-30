package io.dkargo.bcexplorer.collector.client.config;

public interface IApiClient<API_URL extends IApiUrl> {

    String getFullUrl(API_URL api);
}
