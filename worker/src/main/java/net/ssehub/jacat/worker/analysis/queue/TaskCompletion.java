package net.ssehub.jacat.worker.analysis.queue;

import net.ssehub.jacat.api.addon.task.Task;

public interface TaskCompletion {
    void finish(Task result);
}
