package de.unihildesheim.sse.jacatplatform.tools;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v0/tools")
public class ToolsController {

    private InMemoryToolRepository repository;

    public ToolsController(InMemoryToolRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Tool> listTools(@RequestParam("language") Optional<String> clanguage) {
        if (clanguage.isPresent()) {
            return this.repository.findAllByLanguagesContaining(clanguage.get());
        }

        return this.repository.findAll();
    }

    @PostMapping
    public Tool register(@Valid @RequestBody Tool tool) {
        if(this.repository.existsToolByApiBaseUrl(tool.getApiBaseUrl())) {
            throw new ToolAlreadyRegisteredException(tool);
        }

        // check availability --> http ping oder so
        //

        tool.setUuid(UUID.randomUUID().toString());
        return repository.save(tool);
    }

    // GET List<Tool> listTools()                                   /api/v0/tools
    // GET List<Tool> listTools(CodeLanguage cl)                    /api/v0/tools?language=c++
    // GET List<Tool> listTools(ToolsType tt)                       /api/v0/tools?type=PLAGIARISM
    // GET List<Tool> listTools(CodeLanguage cl, ToolsType tt)      /api/v0/tools?language=c++&type=PLAGIARISM

    // GET List<String> listToolTypes()                             /api/v0/tools/types
    // GET List<String> listCodeLanguages()                         /api/v0/tools/languages

    // POST     void register(Tool tool)                            /api/v0/tools
    // DELETE   void unregister(Tool tool)                          /api/v0/tools

    // GET/POST/PUT/PATCH/DELETE     forward(HttpRequest request)   /api/v0/tools/{toolName}/**
}
