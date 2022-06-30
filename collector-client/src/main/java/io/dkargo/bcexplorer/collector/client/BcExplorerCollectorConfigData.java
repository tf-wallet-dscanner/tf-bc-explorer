package io.dkargo.bcexplorer.collector.client;

import io.dkargo.bcexplorer.collector.client.config.AConfigData;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BcExplorerCollectorConfigData extends AConfigData {

    @Builder
    public BcExplorerCollectorConfigData(String apiUrl) { super(apiUrl); }
}
