package de.unihildesheim.sse.jacat.platform.tools;

import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.Addon;
import de.unihildesheim.sse.jacat.api.addon.task.SyncAnalysisTask;
import de.unihildesheim.sse.jacat.api.addon.task.TaskConfiguration;
import de.unihildesheim.sse.jacat.platform.task.AnalysisAddonRegister;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0/tools")
public class ToolsController {

    private AnalysisAddonRegister analysisAddonRegister;

    public ToolsController(AnalysisAddonRegister analysisAddonRegister) {
        this.analysisAddonRegister = analysisAddonRegister;
    }

    // GET List<Tool> listTools()                                   /api/v0/tools
    // GET List<Tool> listTools(CodeLanguage cl)                    /api/v0/tools?language=c++
    // GET List<Tool> listTools(ToolsType tt)                       /api/v0/tools?type=PLAGIARISM
    // GET List<Tool> listTools(CodeLanguage cl, ToolsType tt)      /api/v0/tools?language=c++&type=PLAGIARISM
    @GetMapping
    public List<ListToolDto> listTools(@RequestParam("language") Optional<String> clanguage,
                                @RequestParam("slug") Optional<String> slug) {
        Map<Addon, AbstractAnalysisCapability> addonsWithMetadata = this.analysisAddonRegister.getAddons();
        Set<Addon> addons = addonsWithMetadata.keySet();
        return addons.stream()
                .map(addon -> new ListToolDto(addon, addonsWithMetadata.get(addon)))
                .filter(addon -> clanguage.isEmpty() || addon.getLanguages().contains(clanguage.get().toLowerCase()))
                .filter(addon -> slug.isEmpty() || addon.getSlug().equalsIgnoreCase(slug.get()))
                .collect(Collectors.toList());
    }

    @PostMapping("/{slug}/analysis")
    public void startAnalysis(@PathVariable("slug") String slug,
                              @RequestParam("language") String language,
                              @RequestBody Map<String, Object> body) {
        Map<Addon, AbstractAnalysisCapability> addons = this.analysisAddonRegister.getAddons();
        Optional<Addon> foundAddon = this.analysisAddonRegister.getAddons(slug).stream()
                .filter(addon -> addons.get(addon).getLanguages().contains(language.toLowerCase()))
                .findFirst();
        if (foundAddon.isPresent()) {
            SyncAnalysisTask capability = (SyncAnalysisTask) addons.get(foundAddon.get());
            TaskConfiguration taskConfiguration = new TaskConfiguration(language.toLowerCase(), body);
            capability.startAnalysis(taskConfiguration);
        }
    }

    @GetMapping("/types")
    public List<AbstractAnalysisCapability> listToolType() {
        // TODO: 14.01.2021 Implement mapping from addons to different analysis types
        return Collections.emptyList();
    }

    // GET List<String> listToolTypes()                             /api/v0/tools/types
    // GET List<String> listCodeLanguages()                         /api/v0/tools/languages
}
