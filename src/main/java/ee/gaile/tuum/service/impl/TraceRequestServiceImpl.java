package ee.gaile.tuum.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.gaile.tuum.apiexeption.ApiException;
import ee.gaile.tuum.model.response.TraceResponse;
import ee.gaile.tuum.repository.TraceRequestRepository;
import ee.gaile.tuum.service.TraceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Aleksei Gaile 20-Apr-24
 */
@Service
@RequiredArgsConstructor
public class TraceRequestServiceImpl implements TraceRequestService {
    private final TraceRequestRepository traceRequestRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<TraceResponse> getAllTraceRequests() {
        List<TraceResponse> traceRequests = traceRequestRepository.getAllTraceRequests();

        return convertStringToMap(traceRequests);
    }

    @Override
    public List<TraceResponse> getTraceRequest(String traceId) {
        List<TraceResponse> traceRequests = traceRequestRepository.getTraceRequestsById(traceId);

        return convertStringToMap(traceRequests);
    }

    private List<TraceResponse> convertStringToMap(List<TraceResponse> traceRequests) {
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {
        };

        traceRequests.forEach(trace -> {
            HashMap<String, String> map;
            try {
                map = objectMapper.readValue(trace.getRequest(), typeRef);
            } catch (JsonProcessingException e) {
                throw new ApiException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            trace.getRequestMap().putAll(map);
        });

        return traceRequests;
    }

}
