package me.dennis.test;

import de.unihildesheim.sse.jacat.api.addon.task.AnalysisRequest;
import de.unihildesheim.sse.jacat.api.addon.task.SyncAnalysisTask;
import de.unihildesheim.sse.jacat.api.addon.task.TaskResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JavaHintingAnalysisCapability extends SyncAnalysisTask {

    public JavaHintingAnalysisCapability() {
        super("hinting", Arrays.asList("Java"));
    }

    @Override
    public TaskResult startAnalysis(AnalysisRequest configuration) {
        System.out.println("New Task");
        System.out.println(configuration.getRequest());

        Map<String, Object> task = configuration.getRequest();
        Map<String, Object> response = new HashMap<>();
        task.keySet().forEach(key -> {
            response.put(key.toUpperCase(), task.get(key));
        });

        return new TaskResult(response);
    }
}
