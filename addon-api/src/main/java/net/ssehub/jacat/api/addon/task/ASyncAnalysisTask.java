package net.ssehub.jacat.api.addon.task;

import java.util.List;

public abstract class ASyncAnalysisTask extends AbstractAnalysisCapability {

    /**
     * Dies ist der Standardweg, um eine (asynchrone) Analysefähigkeit zu erstellen.
     * Es muss immer der Slug und die Sprachen angegeben werden. Diese
     * dürfen nicht null sein und dürfen nicht leer sein. Sowohl Slug
     * als auch die Programmiersprachen werden in LowerCase gespeichert.
     * Hierdurch wird das Auffinden, der Analysefähigkeit durch die
     * Plattform unterstützt.
     *
     * @param slug      Der Slug unter der die Analyse gefunden werden soll.
     *                  Dieser darf maximal aus einem Wort bestehen und sollte
     *                  keine Umlaute oder Zahlen verwenden.
     * @param languages Die Sprachen, welche die Analyse abdecken kann.
     */
    public ASyncAnalysisTask(String slug, List<String> languages) {
        super(slug, languages);
    }



    /**
     * Jede Analysefähigkeit muss einen Auslöser für eine neue Analyse
     * besitzen. Mithilfe dieser Methode startet die Plattform die
     * Analyse. Dieser Methode wird eine {@link AnalysisRequest}
     * übergeben, sodass die Analyse parametrisiert gestartet werden
     * kann.
     *
     * Zusätzlich wird ein TaskCompleter mitgegeben, welcher benutzt
     * werden kann um der Plattform mitzuteilen, dass das Addon mit
     * der Analyse fertig ist
     *
     * @param request der Request, welcher die Daten der
     *                      AnalyseAnfrage beinhaltet
     */
    public abstract TaskResult startAnalysis(AnalysisRequest request);
}
