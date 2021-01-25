package de.unihildesheim.sse.jacat.platform.task.batch;

import de.unihildesheim.sse.jacat.api.addon.task.ASyncAnalysisTask;
import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.task.AnalysisRequest;
import de.unihildesheim.sse.jacat.api.addon.task.TaskResult;
import de.unihildesheim.sse.jacat.platform.task.AnalysisCapabilities;
import de.unihildesheim.sse.jacat.platform.task.AnalysisTask;
import de.unihildesheim.sse.jacat.platform.task.TaskJobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class AnalysisTaskItemProcessor implements ItemProcessor<AnalysisTask, AnalysisTask> {

    private static final Logger log = LoggerFactory.getLogger(AnalysisTaskItemProcessor.class);

    private AnalysisCapabilities capabilities;

    public AnalysisTaskItemProcessor(AnalysisCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public AnalysisTask process(AnalysisTask analysisTask) {
        AbstractAnalysisCapability hinting = this.capabilities.getCapability("hinting", "c++");
        if (hinting instanceof ASyncAnalysisTask) {
            ASyncAnalysisTask asyncHinting = (ASyncAnalysisTask) hinting;
            AnalysisRequest request = new AnalysisRequest("c++", analysisTask.getRequest());
            TaskResult taskResult = asyncHinting.startAnalysis(request);
            analysisTask.setResponse(taskResult.getResult());
            analysisTask.setStatus(TaskJobStatus.SUCCESSFUL);
        }
        return analysisTask;
    }

}