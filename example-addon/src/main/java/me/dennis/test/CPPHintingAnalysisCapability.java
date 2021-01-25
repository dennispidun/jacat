package me.dennis.test;

import de.unihildesheim.sse.jacat.api.addon.task.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CPPHintingAnalysisCapability extends ASyncAnalysisTask {

    public CPPHintingAnalysisCapability() {
        super("hinting", Arrays.asList("c++"));
    }

    @Override
    public TaskResult startAnalysis(AnalysisRequest request) {
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
