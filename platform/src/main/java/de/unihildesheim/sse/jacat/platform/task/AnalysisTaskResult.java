package de.unihildesheim.sse.jacat.platform.task;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalysisTaskResult {

    @JsonUnwrapped
    private AnalysisTask createdTask;

}
