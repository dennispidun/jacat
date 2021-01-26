package net.ssehub.jacat.worker.analysis;

import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.worker.analysis.capabilities.AnalysisCapabilities;
import net.ssehub.jacat.worker.analysis.queue.AnalysisTaskScheduler;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v0/analysis")
public class AnalysisController {

    private AnalysisCapabilities analysisCapabilities;
    private AnalysisTaskScheduler analysisTaskScheduler;


    public AnalysisController(AnalysisCapabilities analysisCapabilities,
                              AnalysisTaskScheduler analysisTaskScheduler) {
        this.analysisCapabilities = analysisCapabilities;
        this.analysisTaskScheduler = analysisTaskScheduler;
    }

    @PostMapping()
    public boolean queue(@RequestBody AnalysisTask task) {
        AbstractAnalysisCapability capability = this.analysisCapabilities.getCapability(task);

        if (capability == null) {
            return false;
        }

        boolean succeeded = this.analysisTaskScheduler.trySchedule(task);
        if (succeeded) {
            // ... Error handling
        } else {
            // ...
        }

        return succeeded;

    }

}
