package me.dennis.test;

import net.ssehub.jacat.api.addon.Addon;

public class Main extends Addon {

    private CPPHintingAnalysisCapability hinter;

    @Override
    public void onEnable() {
        this.hinter = new CPPHintingAnalysisCapability();

        this.getJacatPlatform().registerAnalysisTask(this, this.hinter);
        this.getJacatPlatform().registerAnalysisTask(this, new JavaHintingAnalysisCapability());
        this.getJacatPlatform().registerAnalysisTask(this, new CPPHintingAnalysisCapability());
        this.getLogger().info("Addon ist da: " + this.getJacatPlatform().getVersion());
    }
}
