package net.ssehub.jacat.worker;

import net.ssehub.jacat.api.AbstractJacatWorker;
import net.ssehub.jacat.api.addon.Addon;
import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.worker.analysis.capabilities.AnalysisCapabilities;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JacatWorker extends AbstractJacatWorker {

    private AnalysisCapabilities analysisCapabilities;

    public JacatWorker(AnalysisCapabilities analysisCapabilities) {
        this.analysisCapabilities = analysisCapabilities;
    }

    public String getVersion() {
        return "1.2.3";
    }

    @Override
    public void registerAnalysisTask(Addon addon, AbstractAnalysisCapability capability) {
        this.checkAlreadyRegistered(capability.getSlug(), capability.getLanguages());
        this.analysisCapabilities.register(addon, capability);
    }

    private void checkAlreadyRegistered(String slug, List<String> languages) {
        for (String language : languages) {
            if (this.analysisCapabilities.isRegistered(slug, language)) {
                throw new AnalysisCapabilityAlreadyRegisteredException(slug, language);
            }
        }
    }

    private static class AnalysisCapabilityAlreadyRegisteredException extends RuntimeException {
        public AnalysisCapabilityAlreadyRegisteredException(String slug, String language) {
            super("The desired capability (slug=\"" +
                    slug + "\", language=\"" + language +
                    "\") is already registered.");
        }
    }

}
