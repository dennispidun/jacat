package de.unihildesheim.sse.jacat.api;

import de.unihildesheim.sse.jacat.api.addon.task.ASyncAnalysisTask;
import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.Addon;
import de.unihildesheim.sse.jacat.api.addon.task.SyncAnalysisTask;

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
     * Hiermit kann ein synchrone Analyse Task registriert werden. Es ist
     * nicht vorgesehen, dass ein Addon mehrere Analysetasks
     * registriert. Daher muss sichergestellt werden, dass die
     * AnalyseFähigkeit (AnalysisCapability) die gewünschte
     * Analyse vollständig abdecken kann.
     *
     * @param addon Muss das laufende Addon sein
     * @param syncTask Beschreibt die Fähigkeit, die eine
     *                           Analyse besitzt. Wenn eine Analyse
     *                           ansteht, wird ein entsprechendes
     *                           Addon dafür benachrichtigt.
     */
    public abstract void registerAnalysisTask(Addon addon, SyncAnalysisTask syncTask);

    /**
     * Hiermit kann ein asynchrone Analyse Task registriert werden. Es ist
     * nicht vorgesehen, dass ein Addon mehrere Analysetasks
     * registriert. Daher muss sichergestellt werden, dass die
     * AnalyseFähigkeit (AnalysisCapability) die gewünschte
     * Analyse vollständig abdecken kann.
     *
     * @param addon Muss das laufende Addon sein
     * @param aSyncAnalysisTask Beschreibt die Fähigkeit, die eine
     *                          Analyse besitzt. Wenn eine Analyse
     *                          ansteht, wird ein entsprechendes
     *                          Addon dafür benachrichtigt.
     */
    public abstract void registerAnalysisTask(Addon addon, ASyncAnalysisTask aSyncAnalysisTask);


}
