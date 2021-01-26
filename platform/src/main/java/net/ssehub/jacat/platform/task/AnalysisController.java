package net.ssehub.jacat.platform.task;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v0/analysis")
public class AnalysisController {


    @GetMapping("/{id}")
    public AnalysisTaskResult getAnalysisResult(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping()
    public AnalysisTaskResult startAnalysis(@RequestParam("slug") String slug,
                                    @RequestParam("language") String language,
                                    @RequestBody Map<String, Object> request) {
        return null;
    }

}
