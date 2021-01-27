package net.ssehub.jacat.platform.analysis;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ssehub.jacat.api.addon.task.Task;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Document
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalysisTask extends Task {

    public AnalysisTask(Task task) {
        super(task.getId(), task.getSlug(),
                task.getLanguage(), task.getStatus(),
                task.getRequest(), task.getResult());
    }

    public AnalysisTask(String slug, String language, Map<String, Object> request) {
        super(null, slug, language, null, request, null);
    }

    @Override
    @Id
    public String getId() {
        return super.getId();
    }
}
