package de.unihildesheim.sse.jacat.api;

import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.Addon;

/**
 * Diese Klasse beschreibt die Möglichkeiten auf einer
 * Analyse Plattform, die eine Erweiterung (Addon) hat.
 *
 * Über diese Klasse sollen alle Steuerungen vorgenommen
 * werden, die für eine Erweiterung wichtig sein könnten.
 * Beispielsweise werden hier die Analyseaufgaben registriert
 * oder die Datacollector festgelegt.
 */
public abstract class AbstractJacatPlatform {

    /**
     * Gibt die Aktuelle Version der Plattform zurück.
     *
     * @return Version, mit der die Plattform läuft
     */
    public abstract String getVersion();

    /**
     * Hiermit kann ein Analyse Task registriert werden. Es ist
     * nicht vorgesehen, dass ein Addon mehrere Analysetasks
     * registriert. Daher muss sichergestellt werden, dass die
     * AnalyseFähigkeit (AnalysisCapability) die gewünschte
     * Analyse vollständig abdecken kann.
     *
     * @param addon Muss das laufende Addon sein
     * @param analysisCapability Beschreibt die Fähigkeit, die eine
     *                           Analyse besitzt. Wenn eine Analyse
     *                           ansteht, wird ein entsprechendes
     *                           Addon dafür benachrichtigt.
     */
    public abstract void registerSyncAnalysisTask(Addon addon, AbstractAnalysisCapability analysisCapability);

}
