package de.unihildesheim.sse.jacat.platform.task;

import de.unihildesheim.sse.jacat.api.addon.task.*;
import de.unihildesheim.sse.jacat.platform.task.queue.TaskQueue;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v0/analysis")
public class AnalysisController {

    private AnalysisCapabilities analysisCapabilities;
    private AnalysisTaskRepository analysisTaskRepository;

    private TaskQueue taskQueue;

    public AnalysisController(AnalysisCapabilities analysisCapabilities,
                              AnalysisTaskRepository analysisTaskRepository,
                              TaskQueue taskQueue) {
        this.analysisCapabilities = analysisCapabilities;
        this.analysisTaskRepository = analysisTaskRepository;
        this.taskQueue = taskQueue;
    }

    @GetMapping("/{id}")
    public AnalysisTaskResult getAnalysisResult(@PathVariable("id") String id) {
        Optional<AnalysisTask> byId = this.analysisTaskRepository.findById(id);
        return byId.map(AnalysisTaskResult::new).orElse(null);
    }

    @PostMapping()
    public AnalysisTaskResult startAnalysis(@RequestParam("slug") String slug,
                                    @RequestParam("language") String language,
                                    @RequestBody Map<String, Object> request) {
        AbstractAnalysisCapability capability = this.analysisCapabilities.getCapability(slug, language);

        if (capability != null) {
            AnalysisRequest analysisRequest = new AnalysisRequest(language.toLowerCase(), request);

            AnalysisTask analysisTask = new AnalysisTask();
            analysisTask.setStatus(TaskJobStatus.ACCEPTED);
            analysisTask.setRequest(analysisRequest.getRequest());

            analysisTask = this.analysisTaskRepository.save(analysisTask);
            if (capability instanceof SyncAnalysisTask) {
                SyncAnalysisTask syncCapability = (SyncAnalysisTask) capability;
                TaskResult result = syncCapability.startAnalysis(analysisRequest);
                analysisTask.setResponse(result.getResult());
                analysisTask.setStatus(TaskJobStatus.SUCCESSFUL);

                analysisTask = this.analysisTaskRepository.save(analysisTask);
            } else {
                this.taskQueue.queue(analysisTask);
            }

            return new AnalysisTaskResult(analysisTask);

        }
        return null;
    }

}
