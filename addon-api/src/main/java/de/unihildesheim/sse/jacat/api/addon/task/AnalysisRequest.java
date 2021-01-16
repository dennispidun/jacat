package de.unihildesheim.sse.jacat.api.addon.task;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse beschreibt die Konfiguration einer (Analyse-)Aufgabe.
 * Hierbei wird immer die gewünschte Sprache mit reingegeben und
 * zusätzlich ein Mapping von Parametern, die für die Analyse wichtig
 * sind.
 */
public class AnalysisRequest {

    private Map<String, Object> request;

    /**
     * Hauptkonstruktor für die Erstellung des AnalysisRequests.
     * Beinhaltet die Sprache und die Parameter des Tasks selbst
     *
     * @param language Die Programmiersprache, die analysiert werden soll
     * @param request Ein Mapping mit den Einstellungen für einen Task
     */
    public AnalysisRequest(String language, Map<String, Object> request) {
        this.request = request;
        if (this.request == null) {
            this.request = new HashMap<>();
        }

        // unschlüssig, ob dies so Sinn ergibt
        this.request.put("language", language);
    }

    public Map<String, Object> getRequest() {
        return request;
    }
}
