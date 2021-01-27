package net.ssehub.jacat.worker.analysis.queue;

import lombok.extern.slf4j.Slf4j;
import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.Task;
import net.ssehub.jacat.worker.analysis.capabilities.AnalysisCapabilities;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@Slf4j
public class AnalysisTaskExecutor {

    private AnalysisCapabilities capabilities;

    public AnalysisTaskExecutor(AnalysisCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    @Async
    public void process(Task task, TaskCompletion completion) {
        AbstractAnalysisCapability capability = this.capabilities.getCapability(task);

        this.log.info("Started AnalysingTask (#" + task.getId() + "): [slug=\"" + capability.getSlug()
                + "\", language=\"" + task.getLanguage() + "\"]");
        long timeStart = System.currentTimeMillis();

        Task result = capability.run(task);
        long timeEnd = System.currentTimeMillis();
        long time = timeEnd - timeStart;
        log.info("Finished AnalysingTask (#" + result.getId() + ") in "
                + time + "ms with status [" + result.getStatus() + "]");
        completion.finish(result);
    }

}
