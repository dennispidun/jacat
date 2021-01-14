package me.dennis.test;

import de.unihildesheim.sse.jacat.addon.AbstractAnalysisCapability;
import de.unihildesheim.sse.jacat.addon.TaskConfiguration;

import java.util.Arrays;

public class HintingAnalysisCapability extends AbstractAnalysisCapability {

    public HintingAnalysisCapability() {
        super("hinting", Arrays.asList("Java", "c++"));
    }

    @Override
    public void startAnalysis(TaskConfiguration configuration) {
        System.out.println("New Task");
        System.out.println(configuration.getTask());
    }
}
