package de.unihildesheim.sse.jacat;

import de.unihildesheim.sse.jacat.addon.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.addon.Addon;

import java.util.List;

public abstract class AbstractJacatPlatform {

    public abstract String getVersion();

    public abstract void registerAnalysisTask(Addon addon, AbstractAnalysisCapability analysisCapability);

}
