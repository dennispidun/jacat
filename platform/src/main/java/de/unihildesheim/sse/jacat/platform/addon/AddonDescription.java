package de.unihildesheim.sse.jacat.platform.addon;

import java.util.List;

public class AddonDescription {

    private String mainClass;
    private List<String> languages;
    private String type;

    public AddonDescription(String mainClass, List<String> languages, String type) {
        this.mainClass = mainClass;
        this.languages = languages;
        this.type = type;
    }

    public String getMainClass() {
        return mainClass;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getType() {
        return type;
    }
}
