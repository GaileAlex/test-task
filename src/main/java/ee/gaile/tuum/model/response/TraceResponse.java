package ee.gaile.tuum.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksei Gaile 19-Apr-24
 */
@Getter
@Setter
@NoArgsConstructor
public class TraceResponse {

    private String traceId;

    private LocalDateTime date;

    private String type;

    @JsonIgnore
    private String request;

    private Map<String, String> requestMap = new HashMap<>();

}
