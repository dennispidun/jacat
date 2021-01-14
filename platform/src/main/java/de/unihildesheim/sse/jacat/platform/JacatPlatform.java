package de.unihildesheim.sse.jacat.platform;

import de.unihildesheim.sse.jacat.AbstractJacatPlatform;
import de.unihildesheim.sse.jacat.addon.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.addon.Addon;
import de.unihildesheim.sse.jacat.platform.task.AnalysisAddonRegister;
import de.unihildesheim.sse.jacat.platform.task.AnalysisAddonMetadata;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JacatPlatform extends AbstractJacatPlatform {

    private AnalysisAddonRegister analysisAddonRegister;

    public JacatPlatform(AnalysisAddonRegister analysisAddonRegister) {
        this.analysisAddonRegister = analysisAddonRegister;
    }

    public String getVersion() {
        return "1.2.3";
    }

    @Override
    public void registerAnalysisTask(Addon addon, AbstractAnalysisCapability analysisCapability) {
        this.analysisAddonRegister.register(addon, analysisCapability);
    }

}
