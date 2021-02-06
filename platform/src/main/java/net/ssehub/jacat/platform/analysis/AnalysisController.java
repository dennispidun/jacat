package net.ssehub.jacat.platform.analysis;

import net.ssehub.jacat.platform.analysis.api.CreateAnalysisDto;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v0/analysis")
public class AnalysisController {

    private AnalysisService analysisService;
    private AnalysisTaskRepository repository;

    public AnalysisController(AnalysisService analysisService,
                              AnalysisTaskRepository repository) {
        this.analysisService = analysisService;
        this.repository = repository;
    }

    @GetMapping("/{task}")
    public Optional<AnalysisTask> getAnalysis(@PathVariable("task") String taskId) {
        return this.repository.findById(taskId);
    }

    @PostMapping()
    public AnalysisTask startAnalysis(@RequestParam("slug") String slug,
                                      @RequestParam("language") String language,
                                      @RequestBody CreateAnalysisDto createAnalysisDto) {
        return this.analysisService.trySchedule(slug, language, createAnalysisDto);
    }

}
