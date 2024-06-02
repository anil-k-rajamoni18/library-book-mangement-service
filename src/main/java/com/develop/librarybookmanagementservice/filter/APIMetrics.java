package com.develop.librarybookmanagementservice.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static ch.qos.logback.classic.ClassicConstants.REQUEST_METHOD;
import static ch.qos.logback.classic.ClassicConstants.REQUEST_REMOTE_HOST_MDC_KEY;
import static ch.qos.logback.classic.ClassicConstants.REQUEST_REQUEST_URI;
import static ch.qos.logback.classic.ClassicConstants.REQUEST_REQUEST_URL;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class APIMetrics implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        long duration = System.currentTimeMillis() - startTime;

        if (!req.getRequestURI().startsWith("/h2-console/")) {
            log.warn("Request Response Summary: {} {} {} {} {} {}",
                    kv(REQUEST_REMOTE_HOST_MDC_KEY, req.getRemoteHost()),
                    kv(REQUEST_REQUEST_URI, req.getRequestURI()),
                    kv(REQUEST_REQUEST_URL, req.getRequestURL()),
                    kv(REQUEST_METHOD, req.getMethod()),
                    kv("HTTPStatus", res.getStatus()),
                    kv("LATENCY in ms", duration));
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
