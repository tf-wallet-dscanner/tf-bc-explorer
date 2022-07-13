package io.dkargo.bcexplorer.collector.service;

public interface TestByKASService {


    void getKlayAccountAddresses();

    void getAccount(String address);

    void getTransactionCount(String address);

    void getBalance(String address);
}
