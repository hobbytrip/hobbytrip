package capstone.communityservice.global.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LatencyLoggingFilter extends OncePerRequestFilter {

    private final LatencyRecorder latencyRecorder;
    private final QueryInspector queryInspector;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        latencyRecorder.start();

        filterChain.doFilter(request, response);

        double latencyForSeconds = latencyRecorder.getLatencyForSeconds();
        int queryCount = queryInspector.getQueryCount();
        String requestURI = request.getRequestURI();

        log.info("Latency : {}s, Query count : {}, Request URI : {}", latencyForSeconds, queryCount, requestURI);
        MDC.clear();
    }
}
