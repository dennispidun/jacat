package me.dennis.test;

import de.unihildesheim.sse.jacat.addon.Addon;

public class Main extends Addon {

    private HintingAnalysisCapability hinter;

    @Override
    public void onEnable() {
        this.hinter = new HintingAnalysisCapability();

        this.getJacatPlatform().registerAnalysisTask(this, this.hinter);
        System.out.println("[" + this.getDescription().getName() + "] Addon ist da: " + this.getJacatPlatform().getVersion());
    }
}
