package de.unihildesheim.sse.jacat.platform.addon;

import de.unihildesheim.sse.jacat.platform.AbstractJacatPlatform;

public abstract class JavaAddon {

    private AbstractJacatPlatform platform;

    public abstract void onEnable();

    public AbstractJacatPlatform getJacatPlatform() {
        return platform;
    }

}
