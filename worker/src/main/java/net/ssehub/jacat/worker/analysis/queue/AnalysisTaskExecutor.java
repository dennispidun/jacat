package net.ssehub.jacat.worker.analysis.queue;

import lombok.extern.slf4j.Slf4j;
import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.PreparedTask;
import net.ssehub.jacat.api.addon.task.Task;
import net.ssehub.jacat.worker.analysis.TaskPreparer;
import net.ssehub.jacat.worker.analysis.capabilities.AnalysisCapabilities;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@EnableAsync
@Slf4j
public class AnalysisTaskExecutor {

    private AnalysisCapabilities capabilities;
    private TaskPreparer taskPreparer;

    public AnalysisTaskExecutor(AnalysisCapabilities capabilities,
                                TaskPreparer taskPreparer) {
        this.capabilities = capabilities;
        this.taskPreparer = taskPreparer;
    }

    @Async
    public void process(Task task, TaskCompletion completion) {
        AbstractAnalysisCapability capability = this.capabilities.getCapability(task);

        this.log.info("Started AnalysingTask (#" + task.getId() + "): [slug=\"" + capability.getSlug()
                + "\", language=\"" + task.getLanguage() + "\"]");
        long timeStart = System.currentTimeMillis();

        PreparedTask result = new PreparedTask(task);
        try {
            PreparedTask preparedTask = this.taskPreparer.prepare(task);
            result = capability.run(preparedTask);
        } catch(Exception e) {
            result.setFailedResult(Collections.singletonMap("message", e.getMessage()));
        }

        task.setResult(result.getStatus(), result.getResult());
        long timeEnd = System.currentTimeMillis();
        long time = timeEnd - timeStart;

        log.info("Finished AnalysingTask (#" + result.getId() + ") in "
                + time + "ms with status [" + result.getStatus() + "]");
        completion.finish(task);
    }

}
