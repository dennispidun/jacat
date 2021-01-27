package net.ssehub.jacat.worker.analysis.capabilities;

import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.Addon;
import net.ssehub.jacat.api.addon.task.Task;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AnalysisCapabilities {

    private Map<AbstractAnalysisCapability, Addon> capabilities = new HashMap<>();

    public void register(Addon addon, AbstractAnalysisCapability analysisCapability) {
        this.capabilities.put(analysisCapability, addon);
    }

    public Map<AbstractAnalysisCapability, Addon> getCapabilities() {
        return this.capabilities;
    }

    public boolean isRegistered(String slug, String language) {
        try {
            return this.getCapability(slug, language) != null;
        } catch (CapabilityNotFoundException ex) {
            return false;
        }
    }

    public List<AbstractAnalysisCapability> getCapabilities(String slug) {
        return this.capabilities.keySet()
                .stream()
                .filter(capability -> capability.getSlug().equalsIgnoreCase(slug))
                .collect(Collectors.toList());
    }

    public AbstractAnalysisCapability getCapability(Task task) {
        return this.getCapability(task.getSlug(), task.getLanguage());
    }

    public AbstractAnalysisCapability getCapability(String slug, String language) {
        return this.capabilities.keySet()
                .stream()
                .filter(capability -> capability.getSlug().equalsIgnoreCase(slug))
                .filter(capability -> capability.getLanguages().contains(language.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new CapabilityNotFoundException(slug, language));
    }

    private static class CapabilityNotFoundException extends RuntimeException {
        public CapabilityNotFoundException(String slug, String language) {
            super("The desired capability (slug=\"" +
                    slug + "\", language=\"" + language +
                    "\" could not be found.");
        }
    }


}
