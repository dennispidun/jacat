package net.ssehub.jacat.platform.analysis;

import net.ssehub.jacat.api.addon.task.Task;
import net.ssehub.jacat.worker.analysis.capabilities.AnalysisCapabilities;
import net.ssehub.jacat.worker.analysis.queue.AnalysisTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v0/analysis")
public class AnalysisController {

    private AnalysisTaskScheduler analysisTaskScheduler;
    private AnalysisCapabilities capabilities;
    private AnalysisTaskRepository repository;

    public AnalysisController(AnalysisTaskScheduler analysisTaskScheduler,
                              AnalysisCapabilities capabilities,
                              AnalysisTaskRepository repository) {
        this.analysisTaskScheduler = analysisTaskScheduler;
        this.capabilities = capabilities;
        this.repository = repository;
    }

    @GetMapping("/{task}")
    public Optional<AnalysisTask> getAnalysis(@PathVariable("task") String taskId) {
        return this.repository.findById(taskId);
    }

    @PostMapping()
    public AnalysisTask startAnalysis(@RequestParam("slug") String slug,
                                      @RequestParam("language") String language,
                                      @RequestBody Map<String, Object> request) {

        if (!capabilities.isRegistered(slug, language)) {
            throw new CapabilityNotAvailableException(slug, language);
        }

        if (!analysisTaskScheduler.canSchedule()) {
            throw new QueueCapacityLimitReachedException();
        }

        AnalysisTask analysisTask = new AnalysisTask(slug, language, request);
        analysisTask = this.repository.save(analysisTask);

        Task task = new Task(analysisTask.getId(), slug, language, request, (finishedTask) -> {
            this.repository.save(new AnalysisTask(finishedTask));
        });
        analysisTaskScheduler.trySchedule(task);
        return analysisTask;
    }

}
