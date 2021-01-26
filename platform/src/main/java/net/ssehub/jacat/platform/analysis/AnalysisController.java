package net.ssehub.jacat.platform.analysis;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v0/analysis")
public class AnalysisController {

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/{id}")
    public AnalysisTaskResult getAnalysisResult(@PathVariable("id") String id) {
        return restTemplate
                .getForObject("http://localhost:8081/api/v0/analysis/{id}", AnalysisTaskResult.class, id);
    }

    @PostMapping()
    public AnalysisTaskResult startAnalysis(@RequestParam("slug") String slug,
                                    @RequestParam("language") String language,
                                    @RequestBody Map<String, Object> request) {
        return null;
    }

}
