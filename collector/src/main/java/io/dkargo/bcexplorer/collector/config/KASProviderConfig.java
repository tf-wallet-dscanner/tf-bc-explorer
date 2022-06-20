package io.dkargo.bcexplorer.collector.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.groundx.caver_ext_kas.CaverExtKAS;

@Configuration
@RequiredArgsConstructor
public class KASProviderConfig {

    @Value("${kas.chain-id.baobab}")
    private String chainId;

    @Value("${kas.access-key-id}")
    private String accessKeyId;

    @Value("${kas.secret-access-key}")
    private String secretAccessKey;

    @Bean
    @Qualifier("caverExtKas")
    public CaverExtKAS caverExtKAS() {

        CaverExtKAS caverExtKAS = new CaverExtKAS();
        caverExtKAS.initKASAPI(chainId, accessKeyId, secretAccessKey);

        return caverExtKAS;
    }


}
