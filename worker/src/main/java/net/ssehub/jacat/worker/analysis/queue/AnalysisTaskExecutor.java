package net.ssehub.jacat.worker.analysis.queue;

import lombok.extern.slf4j.Slf4j;
import net.ssehub.jacat.api.addon.task.ASyncAnalysisTask;
import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.AnalysisRequest;
import net.ssehub.jacat.api.addon.task.TaskResult;
import net.ssehub.jacat.worker.analysis.AnalysisCapabilities;
import net.ssehub.jacat.worker.analysis.AnalysisTask;
import net.ssehub.jacat.worker.analysis.AnalysisTaskRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@EnableAsync
@Slf4j
public class AnalysisTaskExecutor {

    private AnalysisCapabilities capabilities;
    private AnalysisTaskRepository taskRepository;
    private AtomicInteger currentProcesses = new AtomicInteger(0);

    public AnalysisTaskExecutor(AnalysisCapabilities capabilities, AnalysisTaskRepository taskRepository) {
        this.capabilities = capabilities;
        this.taskRepository = taskRepository;
    }

    @Async
    public void process(AnalysisTask analysisTask) {
        if (analysisTask == null) {
            return;
        }
        if (this.currentProcesses.get() >= 5) {
            return;
        }

        AbstractAnalysisCapability hinting = this.capabilities.getCapability("hinting", "c++");

        if (!(hinting instanceof ASyncAnalysisTask)) {
            return;
        }
        this.log.info("Started AnalysingTask (#" + analysisTask.getId() + "): [slug=\"" + hinting.getSlug()
                + "\", language=\"" + analysisTask.getRequest().get("language") + "\"]");
        this.currentProcesses.incrementAndGet();
        Long timeStart = System.currentTimeMillis();
        analysisTask.setStatus(AnalysisTask.Status.RUNNING);
        analysisTask = taskRepository.save(analysisTask);

        ASyncAnalysisTask asyncHinting = (ASyncAnalysisTask) hinting;
        AnalysisRequest request = new AnalysisRequest("c++", analysisTask.getRequest());
        TaskResult taskResult = asyncHinting.startAnalysis(request);
        analysisTask.setResponse(taskResult.getResult());
        analysisTask.setStatus(AnalysisTask.Status.SUCCESSFUL);
        analysisTask = taskRepository.save(analysisTask);
        Long timeEnd = System.currentTimeMillis();
        Long time = timeEnd - timeStart;
        this.log.info("Finished AnalysingTask (#" + analysisTask.getId() + ") in "
                + time + "ms with status [" + analysisTask.getStatus() + "]");


        this.currentProcesses.decrementAndGet();
    }

}
