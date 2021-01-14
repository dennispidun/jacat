package de.unihildesheim.sse.jacat.addon;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAnalysisCapability {

    private final String slug;
    private final List<String> languages;

    public AbstractAnalysisCapability(String slug, List<String> languages) {
        this.slug = slug;
        this.languages = languages.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public abstract void startAnalysis(TaskConfiguration configuration);

    public List<String> getLanguages() {
        return languages;
    }

    public String getSlug() {
        return slug;
    }
}
