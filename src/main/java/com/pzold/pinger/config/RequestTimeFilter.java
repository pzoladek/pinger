package com.pzold.pinger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Component
@WebFilter("/*")
public class RequestTimeFilter implements Filter {

    @Autowired
    private Map<RequestTimeMapConfiguration.RequestType, Long> requestTimes;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final var currentTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        final var totalTime = System.currentTimeMillis() - currentTime;

        requestTimes.put(RequestTimeMapConfiguration.RequestType.of((HttpMethod.valueOf(((HttpServletRequest) request).getMethod())),
                ((HttpServletRequest) request).getServletPath()),
                totalTime);
    }

    @Override
    public void destroy() {

    }
}
