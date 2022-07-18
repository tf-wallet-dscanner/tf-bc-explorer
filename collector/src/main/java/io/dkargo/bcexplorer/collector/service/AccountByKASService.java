package io.dkargo.bcexplorer.collector.service;

import io.dkargo.bcexplorer.dto.collector.kas.account.response.ResGetAccountDTO;

public interface AccountByKASService {

    ResGetAccountDTO getAccountByAddress(String address);
}
