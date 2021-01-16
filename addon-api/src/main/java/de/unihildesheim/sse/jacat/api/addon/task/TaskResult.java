package de.unihildesheim.sse.jacat.api.addon.task;

import java.util.Map;

public class TaskResult {

    private Map<String, Object> result;

    public TaskResult(Map<String, Object> result) {
        this.result = result;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public Object get(String key) {
        return this.result.get(key);
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "result=" + result +
                '}';
    }
}
