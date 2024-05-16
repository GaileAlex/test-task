package ee.gaile.tuum.repository;

import ee.gaile.tuum.model.response.TraceResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Aleksei Gaile 19-Apr-24
 */
@Mapper
public interface TraceRequestRepository {

    @Insert("INSERT INTO trace_request (trace_id, type, request)" +
            " VALUES (#{traceId}::uuid, #{type}, #{request}::jsonb)")
    void insertRequest(String traceId, String type, String request);

    @Select("SELECT * FROM trace_request")
    @Results({
            @Result(property = "traceId", column = "trace_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "type", column = "type"),
            @Result(property = "request", column = "request")})
    List<TraceResponse> getAllTraceRequests();

    @Select("SELECT * FROM trace_request WHERE trace_id = #{traceId}::uuid")
    @Results({
            @Result(property = "traceId", column = "trace_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "type", column = "type"),
            @Result(property = "request", column = "request")})
    List<TraceResponse> getTraceRequestsById(String traceId);

}
