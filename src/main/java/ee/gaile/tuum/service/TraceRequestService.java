package ee.gaile.tuum.service;

import ee.gaile.tuum.model.response.TraceResponse;

import java.util.List;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
public interface TraceRequestService {

    List<TraceResponse> getAllTraceRequests();

    List<TraceResponse> getTraceRequest(String accountId);

}
