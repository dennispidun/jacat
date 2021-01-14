package de.unihildesheim.sse.jacat.addon;

import de.unihildesheim.sse.jacat.AbstractJacatPlatform;

import java.util.Objects;

/**
 * Diese Klasse beschreibt wie ein Addon funktioniert.
 * Ein Addon sollte diese Klasse erweitern und implementieren,
 * sodass dieses von der Plattform gestartet werden kann.
 */
public abstract class Addon {

    private AbstractJacatPlatform platform;
    private AddonDescription description;

    /**
     * Diese Methode wird zur Initialisierung des Addons genutzt.
     * Hier müssen alle möglichen Einstellungsaufgaben getätigt
     * werden. Erst dann wird das Addon als funktionsfähig angesehen.
     */
    public abstract void onEnable();

    /**
     * Gibt die JacatPlatform zurück. Hierrüber kann das Addon mit
     * der Analyseplattform interagieren.
     * @return Laufende JacatPlatform
     */
    public AbstractJacatPlatform getJacatPlatform() {
        return platform;
    }

    /**
     * Die {@link AddonDescription} beinhaltet alle Informationen,
     * die in der 'addon.yml' angegeben wurden.
     * @return Die AddonDescription des aktuellen Addons
     */
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
