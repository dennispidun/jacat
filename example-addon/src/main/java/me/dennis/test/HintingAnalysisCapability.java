package me.dennis.test;

import de.unihildesheim.sse.jacat.api.addon.task.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.api.addon.task.SyncAnalysisTask;
import de.unihildesheim.sse.jacat.api.addon.task.TaskConfiguration;
import de.unihildesheim.sse.jacat.api.addon.task.TaskResult;

import java.util.Arrays;

public class HintingAnalysisCapability extends SyncAnalysisTask {

    public HintingAnalysisCapability() {
        super("hinting", Arrays.asList("Java", "c++"));
    }

    @Override
    public TaskResult startAnalysis(TaskConfiguration configuration) {
        System.out.println("New Task");
        System.out.println(configuration.getTask());
        return null;
    }
}
