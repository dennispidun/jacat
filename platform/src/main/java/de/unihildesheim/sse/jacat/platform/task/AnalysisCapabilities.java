package de.unihildesheim.sse.jacat.platform.task;

import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.Addon;
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

    public boolean isRegistered(Addon addon) {
        return this.capabilities.containsValue(addon);
    }

    public boolean isRegistered(String slug, String language) {
        try {
            return this.getCapability(slug, language) != null;
        } catch (AddonCapabilityNotFoundException ex) {
            return false;
        }
    }

    public List<AbstractAnalysisCapability> getCapabilities(String slug) {
        return this.capabilities.keySet()
                .stream()
                .filter(capability -> capability.getSlug().equalsIgnoreCase(slug))
                .collect(Collectors.toList());
    }

    public AbstractAnalysisCapability getCapability(String slug, String language) {
        return this.capabilities.keySet()
                .stream()
                .filter(capability -> capability.getSlug().equalsIgnoreCase(slug))
                .filter(capability -> capability.getLanguages().contains(language.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new AddonCapabilityNotFoundException(slug, language));
    }

}
