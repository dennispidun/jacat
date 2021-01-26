package me.dennis.test;

import net.ssehub.jacat.api.addon.task.ASyncAnalysisTask;
import net.ssehub.jacat.api.addon.task.AnalysisRequest;
import net.ssehub.jacat.api.addon.task.TaskResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CPPHintingAnalysisCapability extends ASyncAnalysisTask {

    public CPPHintingAnalysisCapability() {
        super("hinting", Arrays.asList("c++"));
    }

    @Override
    public TaskResult startAnalysis(AnalysisRequest request) {
        int sek = (int) (Math.random() * 50 + 10);

        try {
            Thread.sleep(1000 * sek);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("New Task");
        System.out.println(request.getRequest());

        Map<String, Object> task = request.getRequest();
        Map<String, Object> response = new HashMap<>();
        task.keySet().forEach(key -> {
            response.put(key.replace("a", "b")
                            .replace("c", "d")
                            .replace("e", "f")
                            .replace("g", "h")
                            .replace("i", "j")
                            .replace("k", "l")
                            .replace("m", "n")
                            .replace("o", "p")
                            .replace("q", "r")
                            .replace("s", "t")
                            .replace("u", "v")
                            .replace("w", "x")
                            .replace("y", "z")
                    , task.get(key));
        });

        TaskResult result = new TaskResult(response);
        return result;
    }
}
