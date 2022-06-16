package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.response.ResTestDTO;

public interface TestService {

    ResTestDTO test(String testValue);
}
