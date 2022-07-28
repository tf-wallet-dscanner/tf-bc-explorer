package io.dkargo.bcexplorer.collector.client;

import io.dkargo.bcexplorer.collector.client.config.IApiUrl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString(of = "url")
@NoArgsConstructor
public enum BcExplorerCollectorUrl implements IApiUrl {

    COLLECTOR_BY_KAS("/block-by-kas"),

    CREATE_BLOCK_CHAIN_INFO_BY_KAS(COLLECTOR_BY_KAS.getUrl() + "/blocks/by-scheduler");

    private String url;

    @Override
    public String getUrl() { return this.url; }
}
