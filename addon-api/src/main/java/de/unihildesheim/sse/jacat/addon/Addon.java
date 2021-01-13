package de.unihildesheim.sse.jacat.addon;

import de.unihildesheim.sse.jacat.AbstractJacatPlatform;

public abstract class Addon {

    private AbstractJacatPlatform platform;
    private AddonDescription description;

    public abstract void onEnable();

    public AbstractJacatPlatform getJacatPlatform() {
        return platform;
    }

    public AddonDescription getDescription() {
        return description;
    }
}
