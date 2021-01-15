package de.unihildesheim.sse.jacat.platform.task;

import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.Addon;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AnalysisAddonRegister {

    private Map<Addon, AbstractAnalysisCapability> addons = new HashMap<>();

    public void register(Addon addon, AbstractAnalysisCapability analysisCapability) {
        this.addons.put(addon, analysisCapability);
    }

    public Map<Addon, AbstractAnalysisCapability>  getAddons() {
        return this.addons;
    }

    public List<Addon> getAddons(String slug) {
        return this.addons.keySet()
                .stream()
                .filter(addon -> this.addons.get(addon).getSlug().equalsIgnoreCase(slug))
                .collect(Collectors.toList());
    }
}
