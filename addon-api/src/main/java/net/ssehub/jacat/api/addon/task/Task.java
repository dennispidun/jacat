package net.ssehub.jacat.api.addon.task;

import java.util.Map;

/**
 * Diese Klasse beschreibt die Konfiguration einer (Analyse-)Aufgabe.
 * Hierbei wird immer die gewünschte Sprache mit reingegeben und
 * zusätzlich ein Mapping von Parametern, die für die Analyse wichtig
 * sind.
 */
public class Task {

    private String id;

    private String slug;

    private String language;

    private Task.Status status;

    private TaskFinish finish;

    private Map<String, Object> request;

    private Map<String, Object> result;

    public Task(String id, String slug, String language, Map<String, Object> request, TaskFinish finish) {
        this.id = id;
        this.slug = slug;
        this.language = language;
        this.request = request;
        this.finish = finish;
    }

    public Task(String slug, String language, Map<String, Object> request, TaskFinish finish) {
        this.slug = slug;
        this.language = language;
        this.request = request;
        this.finish = finish;
    }

    public Task() {
    }

    public Task(String id,
                String slug,
                String language,
                Status status,
                Map<String, Object> request,
                Map<String, Object> result) {
        this.id = id;
        this.slug = slug;
        this.language = language;
        this.status = status;
        this.request = request;
        this.result = result;
    }

    public Map<String, Object> getRequest() {
        return request;
    }

    public String getSlug() {
        return slug;
    }

    public String getLanguage() {
        return language;
    }

    public void setSuccessfulResult(Map<String, Object> result) {
        this.result = result;
        this.status = Status.SUCCESSFUL;
    }

    public void setFailedResult(Map<String, Object> result) {
        this.result = result;
        this.status = Status.FAILED;
    }

    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void finish() {
        this.finish.finish(this);
    }
    public enum Status {
        SUCCESSFUL(),
        FAILED();
    }
}
