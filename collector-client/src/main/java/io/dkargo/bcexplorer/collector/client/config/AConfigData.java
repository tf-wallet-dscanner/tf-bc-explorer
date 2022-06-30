package io.dkargo.bcexplorer.collector.client.config;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PUBLIC)
public abstract class AConfigData {

    protected String apiUrl;

    protected AConfigData(String apiUrl) {this.apiUrl = apiUrl; }
}
