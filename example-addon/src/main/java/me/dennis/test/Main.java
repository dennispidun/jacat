package me.dennis.test;

import de.unihildesheim.sse.jacat.addon.Addon;

public class Main extends Addon {

    private HintingAnalysisCapability hinter;

    @Override
    public void onEnable() {
        this.hinter = new HintingAnalysisCapability();

        this.getJacatPlatform().registerAnalysisTask(this, this.hinter);
        this.getLogger().info("Addon ist da: " + this.getJacatPlatform().getVersion());
    }
}
