package de.unihildesheim.sse.jacat.platform.task;

import de.unihildesheim.sse.jacat.api.addon.task.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v0/tools/{slug}/analysis")
public class AnalysisController {

    private AnalysisCapabilities analysisCapabilities;

    public AnalysisController(AnalysisCapabilities analysisCapabilities) {
        this.analysisCapabilities = analysisCapabilities;
    }

    @PostMapping()
    public TaskResult startAnalysis(@PathVariable("slug") String slug,
                                    @RequestParam("language") String language,
                                    @RequestBody Map<String, Object> body) {
        AbstractAnalysisCapability capability = this.analysisCapabilities.getCapability(slug, language);

        if (capability != null) {
            if (capability instanceof SyncAnalysisTask) {
                SyncAnalysisTask syncCapability = (SyncAnalysisTask) capability;
                AnalysisRequest analysisRequest = new AnalysisRequest(language.toLowerCase(), body);
                return syncCapability.startAnalysis(analysisRequest);
            } else if (capability instanceof ASyncAnalysisTask) {
                ASyncAnalysisTask syncCapability = (ASyncAnalysisTask) capability;
                AnalysisRequest analysisRequest = new AnalysisRequest(language.toLowerCase(), body);
                syncCapability.startAnalysis(analysisRequest, System.out::println);
            } // Handle ASync Capabilities
        }
        return null;
    }

}
