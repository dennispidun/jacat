package me.dennis.test;

import de.unihildesheim.sse.jacat.platform.addon.JavaAddon;

public class Main extends JavaAddon {

    @Override
    public void onEnable() {
        System.out.println("Noch ein Addon isssss da: " + this.getJacatPlatform().getVersion());
    }
}
