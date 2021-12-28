package com.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class TimingFilter extends OncePerRequestFilter {

    /** {@code true} if query parameters should be logged. */
    private boolean includeQueryParams = true;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        }catch (Exception e){
            log.error("Error while filtering : {}", e);
        }finally {
            if( logger.isInfoEnabled() ) {
                final long end = System.currentTimeMillis();
                logger.info(buildMessage(request, (end - start)));
            }
        }
    }

    /**
     * Builds the message to log from the specified {@code request} including
     * the {@code executionTime}.
     *
     * @param request
     * @param executionTime in timesInMilis
     * @return log message
     */
    private String buildMessage(final HttpServletRequest request, final long executionTime) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("method=").append(request.getMethod());
        buffer.append(", uri=").append(request.getRequestURI());
        if( includeQueryParams && request.getQueryString() != null ) {
            buffer.append('?').append(request.getQueryString());
        }
        buffer.append(", executionTime=").append(executionTime);
        buffer.append(" ms");
        return buffer.toString();
    }
}
