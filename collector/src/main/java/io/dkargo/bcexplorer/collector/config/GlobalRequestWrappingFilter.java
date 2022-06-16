package io.dkargo.bcexplorer.collector.config;

import log.munzi.interceptor.ReadableRequestWrapper;
import log.munzi.interceptor.config.ApiLogProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class GlobalRequestWrappingFilter implements Filter {

    private final ApiLogProperties apiLog;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        List<String> secretApiList = new ArrayList<>();
        String maxSize = "";
        if (apiLog.getRequest() != null) {
            secretApiList = apiLog.getRequest().getSecretApi();
            maxSize = apiLog.getRequest().getMaxBodySize();
        }

        HttpServletRequest wrappingRequest = new ReadableRequestWrapper((HttpServletRequest) request, secretApiList, maxSize);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(wrappingRequest, wrappingResponse);

        wrappingResponse.copyBodyToResponse(); // 캐시를 copy해 return될 response body에 저장
    }
}
