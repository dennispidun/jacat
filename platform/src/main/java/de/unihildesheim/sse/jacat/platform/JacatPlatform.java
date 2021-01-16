package de.unihildesheim.sse.jacat.platform;

import de.unihildesheim.sse.jacat.api.AbstractJacatPlatform;
import de.unihildesheim.sse.jacat.api.addon.task.ASyncAnalysisTask;
import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.Addon;
import de.unihildesheim.sse.jacat.api.addon.task.SyncAnalysisTask;
import de.unihildesheim.sse.jacat.platform.addon.AnalysisCapabilityAlreadyRegisteredException;
import de.unihildesheim.sse.jacat.platform.task.AnalysisCapabilities;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JacatPlatform extends AbstractJacatPlatform {

    private AnalysisCapabilities analysisCapabilities;

    public JacatPlatform(AnalysisCapabilities analysisCapabilities) {
        this.analysisCapabilities = analysisCapabilities;
    }

    public String getVersion() {
        return "1.2.3";
    }

    @Override
    public void registerAnalysisTask(Addon addon, SyncAnalysisTask syncTask) {
        this.registerAnAnalysisTask(addon, syncTask);
    }

    @Override
    public void registerAnalysisTask(Addon addon, ASyncAnalysisTask asyncTask) {
        this.registerAnAnalysisTask(addon, asyncTask);
    }

    private void registerAnAnalysisTask(Addon addon, AbstractAnalysisCapability analysisCapability) {
        this.checkAlreadyRegistered(analysisCapability.getSlug(), analysisCapability.getLanguages());
        this.analysisCapabilities.register(addon, analysisCapability);
    }

    private void checkAlreadyRegistered(String slug, List<String> languages) {
        for (String language : languages) {
            if (this.analysisCapabilities.isRegistered(slug, language)) {
                throw new AnalysisCapabilityAlreadyRegisteredException(slug, language);
            }
        }
    }

}
