package de.unihildesheim.sse.jacat.addon;

import de.unihildesheim.sse.jacat.AbstractJacatPlatform;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addon addon = (Addon) o;
        return description.equals(addon.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
