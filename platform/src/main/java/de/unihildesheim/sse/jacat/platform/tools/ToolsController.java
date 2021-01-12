package de.unihildesheim.sse.jacat.platform.tools;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0/tools")
public class ToolsController {

    private InMemoryToolRepository repository;
    private ToolsAvailabilityService toolsAvailabilityService;

    public ToolsController(InMemoryToolRepository repository, ToolsAvailabilityService toolsAvailabilityService) {
        this.repository = repository;
        this.toolsAvailabilityService = toolsAvailabilityService;
    }

    // GET List<Tool> listTools()                                   /api/v0/tools
    // GET List<Tool> listTools(CodeLanguage cl)                    /api/v0/tools?language=c++
    // GET List<Tool> listTools(ToolsType tt)                       /api/v0/tools?type=PLAGIARISM
    // GET List<Tool> listTools(CodeLanguage cl, ToolsType tt)      /api/v0/tools?language=c++&type=PLAGIARISM
    @GetMapping
    public List<ListToolDto> listTools(@RequestParam("language") Optional<String> clanguage,
                                @RequestParam("type") Optional<String> type) {
        List<Tool> tools;

        if (clanguage.isPresent() && type.isEmpty()) {
            tools = this.repository.findAllByLanguagesContaining(clanguage.get());
        } else if (clanguage.isPresent() && type.isPresent()) {
            tools = this.repository.findAllByTypeAndLanguagesContaining(type.get(), clanguage.get());
        } else if (clanguage.isEmpty() && type.isPresent()) {
            tools = this.repository.findAllByType(type.get());
        } else {
            tools = this.repository.findAll();
        }

        return tools.stream()
                .filter(tool -> !tool.isFailedAvailabilityTest())
                .map(ListToolDto::new)
                .collect(Collectors.toList());
    }

//    @PostMapping
//    public Tool register(@Valid @RequestBody Tool tool) {
//        if (this.repository.existsToolByApiBaseUrl(tool.getApiBaseUrl())) {
//            throw new ToolAlreadyRegisteredException(tool);
//        }
//
//        this.toolsAvailabilityService.checkAvailability(tool);
//
//        tool.setUuid(UUID.randomUUID().toString());
//        return repository.save(tool);
//    }

    @GetMapping("/types")
    public List<Object> listToolType() {
        List<String> types = repository.findAll().stream()
                .map(Tool::getType)
                .distinct()
                .collect(Collectors.toList());

        return types.stream().map(type -> {
            Map<String, Object> result = new HashMap<>();
            result.put("type", type);
            List<String> languages = repository.findAllLanguagesByType(type);
            result.put("languages", languages);
            return result;
        }).collect(Collectors.toList());

    }

    // GET List<String> listToolTypes()                             /api/v0/tools/types
    // GET List<String> listCodeLanguages()                         /api/v0/tools/languages

    // POST     void register(Tool tool)                            /api/v0/tools
    // DELETE   void unregister(Tool tool)                          /api/v0/tools

    // GET/POST/PUT/PATCH/DELETE     forward(HttpRequest request)   /api/v0/tools/{toolName}/**
}
