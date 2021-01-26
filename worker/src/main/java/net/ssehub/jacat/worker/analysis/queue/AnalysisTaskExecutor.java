package net.ssehub.jacat.worker.analysis.queue;

import lombok.extern.slf4j.Slf4j;
import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.AnalysisRequest;
import net.ssehub.jacat.api.addon.task.TaskResult;
import net.ssehub.jacat.worker.analysis.capabilities.AnalysisCapabilities;
import net.ssehub.jacat.worker.analysis.AnalysisTask;
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
    public void process(AnalysisTask analysisTask, AnalysisTaskScheduler.FinishCallback cb) {
        AbstractAnalysisCapability hinting = this.capabilities.getCapability("hinting", "c++");

        this.log.info("Started AnalysingTask (#" + analysisTask.getId() + "): [slug=\"" + hinting.getSlug()
                + "\", language=\"" + analysisTask.getRequest().get("language") + "\"]");
        Long timeStart = System.currentTimeMillis();

        AnalysisRequest request = new AnalysisRequest("c++", analysisTask.getRequest());
        TaskResult taskResult = hinting.startAnalysis(request);
        analysisTask.setSuccessfulResponse(taskResult.getResult());
        Long timeEnd = System.currentTimeMillis();
        Long time = timeEnd - timeStart;
        this.log.info("Finished AnalysingTask (#" + analysisTask.getId() + ") in "
                + time + "ms with status [" + analysisTask.getStatus() + "]");
        cb.finish();
    }

}
