package ee.gaile.tuum.controller;

import ee.gaile.tuum.model.response.TraceResponse;
import ee.gaile.tuum.service.TraceRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/trace-request")
@Tag(name = "TraceRequestController", description = "Controller for managing requests")
public class TraceRequestController {
    private final TraceRequestService traceRequestService;

    @GetMapping
    @Operation(summary = "Service for obtaining a list of all requests")
    public ResponseEntity<List<TraceResponse>> getAllTraceRequests() {
        return new ResponseEntity<>(traceRequestService.getAllTraceRequests(), HttpStatus.OK);
    }

    @GetMapping("/{traceId}")
    @Operation(summary = "Service for obtaining a list of requests by identifier")
    public ResponseEntity<List<TraceResponse>> getTraceRequest(@PathVariable String traceId) {
        return new ResponseEntity<>(traceRequestService.getTraceRequest(traceId), HttpStatus.OK);
    }

}
