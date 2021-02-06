package net.ssehub.jacat.worker.analysis.queue;

import net.ssehub.jacat.api.addon.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnalysisTaskSchedulerTest {

    private AnalysisTaskScheduler analysisTaskScheduler;

    @BeforeEach
    void setUp() {
        analysisTaskScheduler = new AnalysisTaskScheduler(1);
    }

    @Test
    void testMaxCapacity() {
        int queueCapacity = 2;
        analysisTaskScheduler = new AnalysisTaskScheduler(queueCapacity);
        boolean first = analysisTaskScheduler.trySchedule(new Task());
        boolean second = analysisTaskScheduler.trySchedule(new Task());
        boolean third = analysisTaskScheduler.trySchedule(new Task());

        assertTrue(first);
        assertTrue(second);
        assertFalse(third);

    }
}