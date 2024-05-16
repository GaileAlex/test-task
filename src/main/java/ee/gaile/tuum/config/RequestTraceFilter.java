package ee.gaile.tuum.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.gaile.tuum.repository.TraceRequestRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Collections.list;

/**
 * @author Aleksei Gaile 19-Apr-24
 */
@Slf4j
@Component
@Profile({"!test"})
@RequiredArgsConstructor
public class RequestTraceFilter extends GenericFilterBean {
    private final TraceRequestRepository traceRequestRepository;
    private final ObjectMapper objectMapper;

    private static final List<String> excludedPaths = new ArrayList<>();
    static {
        excludedPaths.add("/trace-request");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = requestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = responseWrapper(response);

        chain.doFilter(requestWrapper, responseWrapper);

        String path = ((RequestFacade) request).getServletPath();
        if (!excludedPaths.contains(path)) {
            String traceId = UUID.randomUUID().toString();

            Map<String, String> headers = logRequest(requestWrapper);
            headers.put("path", path);
            String jsonRequest = objectMapper.writeValueAsString(headers);
            traceRequestRepository.insertRequest(traceId, "request", jsonRequest);

            Map<String, String> headersResponse = logResponse(responseWrapper);
            String jsonResponse = objectMapper.writeValueAsString(headersResponse);
            traceRequestRepository.insertRequest(traceId, "response", jsonResponse);
        } else {
            responseWrapper.copyBodyToResponse();
        }
    }

    private Map<String, String> logRequest(ContentCachingRequestWrapper request) {
        Map<String, String> headers = getHeaders(list(request.getHeaderNames()), request::getHeader);
        headers.put("body", new String(request.getContentAsByteArray()));

        return headers;
    }

    private Map<String, String> logResponse(ContentCachingResponseWrapper response) throws IOException {
        Map<String, String> headers = getHeaders(response.getHeaderNames(), response::getHeader);
        headers.put("body", new String(response.getContentAsByteArray()));
        response.copyBodyToResponse();

        return headers;
    }

    private Map<String, String> getHeaders(Collection<String> headerNames, Function<String, String> headerValueResolver) {
        Map<String, String> headers = new HashMap<>();
        for (String headerName : headerNames) {
            String header = headerValueResolver.apply(headerName);
            headers.put(headerName, header);
        }

        return headers;
    }

    private ContentCachingRequestWrapper requestWrapper(ServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper requestWrapper) {
            return requestWrapper;
        }

        return new ContentCachingRequestWrapper((HttpServletRequest) request);
    }

    private ContentCachingResponseWrapper responseWrapper(ServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper responseWrapper) {
            return responseWrapper;
        }

        return new ContentCachingResponseWrapper((HttpServletResponse) response);
    }

}


