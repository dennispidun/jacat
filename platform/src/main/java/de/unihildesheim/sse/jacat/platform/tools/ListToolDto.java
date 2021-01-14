package de.unihildesheim.sse.jacat.platform.tools;

import de.unihildesheim.sse.jacat.addon.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.addon.Addon;
import de.unihildesheim.sse.jacat.platform.task.AnalysisAddonMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListToolDto {

    private String name;

    private String slug;

    private List<String> languages;

    public ListToolDto(Addon addon, AbstractAnalysisCapability analysisCapability) {
        this.name = addon.getDescription().getName();
        this.slug = analysisCapability.getSlug();
        this.languages = analysisCapability.getLanguages();
    }
}
