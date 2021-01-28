package net.ssehub.jacat.platform.analysis;

import net.ssehub.jacat.api.addon.task.Task;
import net.ssehub.jacat.worker.analysis.capabilities.AnalysisCapabilities;
import net.ssehub.jacat.worker.analysis.queue.AnalysisTaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class AnalysisMonitor {

    private AnalysisCapabilities capabilities;
    private AnalysisTaskRepository repository;
    private AnalysisTaskScheduler scheduler;
    private AnalysisService analysisService;

    public AnalysisMonitor(AnalysisCapabilities capabilities,
                           AnalysisTaskRepository repository,
                           AnalysisTaskScheduler scheduler,
                           AnalysisService analysisService) {
        this.capabilities = capabilities;
        this.repository = repository;
        this.scheduler = scheduler;
        this.analysisService = analysisService;
    }

    @Scheduled(cron = "*/30 * * * * *")
    private void checkAndRerun() {
        List<AnalysisTask> unprocessed= this.repository.findAllByStatus(null);
        unprocessed.stream().map(analysisTask ->
                        new Task(analysisTask.getId(),
                        analysisTask.getSlug(),
                        analysisTask.getLanguage(),
                        analysisTask.getStatus(),
                        analysisTask.getDataConfiguration(),
                        analysisTask.getRequest(),
                        analysisTask.getResult()))
                .filter(task -> !scheduler.isQueued(task) && !scheduler.isRunning(task))
                .filter(task -> capabilities.getCapability(task) != null)
                .forEach(task -> analysisService.schedule(task));

    }

}
