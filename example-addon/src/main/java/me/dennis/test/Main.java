package me.dennis.test;

import de.unihildesheim.sse.jacat.addon.Addon;

public class Main extends Addon {

    @Override
    public void onEnable() {
        System.out.println("[" + this.getDescription().getName() + "] Addon ist da: " + this.getJacatPlatform().getVersion());
    }
}
