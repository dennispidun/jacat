package de.unihildesheim.sse.jacat.api.addon.task;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse beschreibt die Konfiguration einer (Analyse-)Aufgabe.
 * Hierbei wird immer die gewünschte Sprache mit reingegeben und
 * zusätzlich ein Mapping von Parametern, die für die Analyse wichtig
 * sind.
 */
public class TaskConfiguration {

    private Map<String, Object> task;

    /**
     * Hauptkonstruktor für die Erstellung der TaskConfiguration.
     * Beinhaltet die Sprache und die Parameter des Tasks selbst
     *
     * @param language Die Programmiersprache, die analysiert werden soll
     * @param task Ein Mapping mit den Einstellungen für einen Task
     */
    public TaskConfiguration(String language, Map<String, Object> task) {
        this.task = task;
        if (this.task == null) {
            this.task = new HashMap<>();
        }

        // unschlüssig, ob dies so Sinn ergibt
        this.task.put("language", language);
    }

    public Map<String, Object> getTask() {
        return task;
    }
}
