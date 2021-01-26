package net.ssehub.jacat.worker.analysis;

import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.AnalysisRequest;
import net.ssehub.jacat.api.addon.task.SyncAnalysisTask;
import net.ssehub.jacat.api.addon.task.TaskResult;
import net.ssehub.jacat.worker.analysis.queue.TaskQueue;
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
    public AnalysisResult getAnalysisResult(@PathVariable("id") String id) {
        Optional<AnalysisTask> byId = this.analysisTaskRepository.findById(id);
        return byId.map(AnalysisResult::new).orElse(null);
    }

    @PostMapping()
    public AnalysisResult startAnalysis(@RequestParam("slug") String slug,
                                        @RequestParam("language") String language,
                                        @RequestBody Map<String, Object> request) {
        AbstractAnalysisCapability capability = this.analysisCapabilities.getCapability(slug, language);

        if (capability != null) {
            AnalysisRequest analysisRequest = new AnalysisRequest(language.toLowerCase(), request);

            AnalysisTask analysisTask = new AnalysisTask();
            analysisTask.setStatus(AnalysisTask.Status.ACCEPTED);
            analysisTask.setRequest(analysisRequest.getRequest());

            analysisTask = this.analysisTaskRepository.save(analysisTask);
            if (capability instanceof SyncAnalysisTask) {
                SyncAnalysisTask syncCapability = (SyncAnalysisTask) capability;
                TaskResult result = syncCapability.startAnalysis(analysisRequest);
                analysisTask.setResponse(result.getResult());
                analysisTask.setStatus(AnalysisTask.Status.SUCCESSFUL);

                analysisTask = this.analysisTaskRepository.save(analysisTask);
            } else {
                this.taskQueue.queue(analysisTask);
            }

            return new AnalysisResult(analysisTask);

        }
        return null;
    }

}
