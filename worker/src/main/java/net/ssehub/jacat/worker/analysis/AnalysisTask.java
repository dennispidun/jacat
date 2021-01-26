package net.ssehub.jacat.worker.analysis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisTask {

    @Id
    private String id;

    private Status status;

    private Map<String, Object> request;
    private Map<String, Object> response;

    public enum Status {

        QUEUED(),
        RUNNING(),
        SUCCESSFUL(),
        FAILED()

    }


}
