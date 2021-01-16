package de.unihildesheim.sse.jacat.platform.tools;

import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.Addon;
import de.unihildesheim.sse.jacat.platform.task.AnalysisCapabilities;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0/tools")
public class ToolsController {

    private AnalysisCapabilities analysisCapabilities;

    public ToolsController(AnalysisCapabilities analysisCapabilities) {
        this.analysisCapabilities = analysisCapabilities;
    }

    // GET List<Tool> listTools()                                   /api/v0/tools
    // GET List<Tool> listTools(CodeLanguage cl)                    /api/v0/tools?language=c++
    // GET List<Tool> listTools(ToolsType tt)                       /api/v0/tools?type=PLAGIARISM
    // GET List<Tool> listTools(CodeLanguage cl, ToolsType tt)      /api/v0/tools?language=c++&type=PLAGIARISM
    @GetMapping
    public List<ListToolDto> listTools(@RequestParam("language") Optional<String> clanguage,
                                @RequestParam("slug") Optional<String> slug) {
        Map<AbstractAnalysisCapability, Addon> capabilities = this.analysisCapabilities.getCapabilities();
        return capabilities.keySet()
                .stream()
                .map(capability -> new ListToolDto(capabilities.get(capability), capability))
                .filter(addon -> clanguage.isEmpty() || addon.getLanguages().contains(clanguage.get().toLowerCase()))
                .filter(addon -> slug.isEmpty() || addon.getSlug().equalsIgnoreCase(slug.get()))
                .collect(Collectors.toList());
    }

    @GetMapping("/types")
    public List<AbstractAnalysisCapability> listToolType() {
        // TODO: 14.01.2021 Implement mapping from addons to different analysis types
        return Collections.emptyList();
    }

    // GET List<String> listToolTypes()                             /api/v0/tools/types
    // GET List<String> listCodeLanguages()                         /api/v0/tools/languages
}
