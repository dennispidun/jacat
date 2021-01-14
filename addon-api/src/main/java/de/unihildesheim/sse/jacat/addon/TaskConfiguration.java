package de.unihildesheim.sse.jacat.addon;

import java.util.HashMap;
import java.util.Map;

public class TaskConfiguration {

    private Map<String, Object> task;

    public TaskConfiguration(String language, Map<String, Object> task) {
        this.task = task;
        if (this.task == null) {
            this.task = new HashMap<>();
        }

        this.task.put("language", language);
    }

    public Map<String, Object> getTask() {
        return task;
    }

    public void setTask(Map<String, Object> task) {
        this.task = task;
    }
}
