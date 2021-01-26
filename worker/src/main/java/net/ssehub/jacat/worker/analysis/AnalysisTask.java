package net.ssehub.jacat.worker.analysis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnalysisTask {

    @NonNull
    private String id;

    private Status status;

    private String slug;

    private String language;

    @NonNull
    private Map<String, Object> request;

    private Map<String, Object> response;

    public void setSuccessfulResponse(Map<String, Object> response) {
        this.status = Status.SUCCESSFUL;
        this.response = response;
    }

    public void setFailedResponse(Map<String, Object> response) {
        this.status = Status.FAILED;
        this.response = response;
    }

    public enum Status {
        SUCCESSFUL(),
        FAILED()
    }


}
