package me.dennis.test;

import net.ssehub.jacat.api.addon.Addon;

public class Main extends Addon {


    @Override
    public void onEnable() {
        this.getJacatPlatform().registerAnalysisTask(this, new JavaHintingAnalysisCapability());
        this.getJacatPlatform().registerAnalysisTask(this, new CPPHintingAnalysisCapability());
        this.getLogger().info("Addon ist da: " + this.getJacatPlatform().getVersion());
    }
}
