package net.ssehub.jacat.platform.analysis;

import net.ssehub.jacat.api.analysis.IAnalysisCapabilities;
import net.ssehub.jacat.api.addon.task.Task;
import net.ssehub.jacat.api.analysis.IAnalysisScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class AnalysisMonitor {

    private IAnalysisCapabilities capabilities;
    private AnalysisTaskRepository repository;
    private IAnalysisScheduler scheduler;
    private AnalysisService analysisService;

    public AnalysisMonitor(IAnalysisCapabilities capabilities,
                           AnalysisTaskRepository repository,
                           IAnalysisScheduler scheduler,
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
                .filter(task -> !scheduler.isScheduled(task) && !scheduler.isRunning(task))
                .filter(task -> capabilities.isRegistered(task.getSlug(), task.getLanguage()))
                .forEach(task -> analysisService.schedule(task));

    }

}
