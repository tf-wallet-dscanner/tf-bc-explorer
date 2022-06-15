package io.dkargo.bcexplorer.api.service;

import io.dkargo.bcexplorer.dto.api.response.ResTestDTO;

public interface TestService {

    ResTestDTO test(String testValue);
}
