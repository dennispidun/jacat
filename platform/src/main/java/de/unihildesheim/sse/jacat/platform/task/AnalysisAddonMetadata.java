package de.unihildesheim.sse.jacat.platform.task;

import java.util.List;

public class AnalysisAddonMetadata {

    private List<String> languages;
    private String slug;

    public AnalysisAddonMetadata(List<String> languages, String slug) {
        this.languages = languages;
        this.slug = slug;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getSlug() {
        return slug;
    }
}
