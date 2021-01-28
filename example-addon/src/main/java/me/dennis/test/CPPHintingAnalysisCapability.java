package me.dennis.test;

import net.ssehub.jacat.api.addon.data.Submission;
import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.PreparedTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPPHintingAnalysisCapability extends AbstractAnalysisCapability {

    public CPPHintingAnalysisCapability() {
        super("hinting", List.of("c++"), 0.1F);
    }

    @Override
    public PreparedTask run(PreparedTask request) {
        for(Submission submission : request.getSubmissions()) {
            System.out.println("submission.getBasePath() = " + submission.getBasePath());
        }


        int sek = (int) (Math.random() * 50 + 10);

        try {
            Thread.sleep(1000L * sek);
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

        request.setSuccessfulResult(response);
        return request;
    }
}
