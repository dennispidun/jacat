package me.dennis.test;

import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.AnalysisRequest;
import net.ssehub.jacat.api.addon.task.TaskResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JavaHintingAnalysisCapability extends AbstractAnalysisCapability {

    public JavaHintingAnalysisCapability() {
        super("hinting", Arrays.asList("Java"), 1.0);
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
