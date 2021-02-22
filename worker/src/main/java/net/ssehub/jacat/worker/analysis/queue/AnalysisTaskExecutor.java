package net.ssehub.jacat.worker.analysis.queue;

import lombok.extern.slf4j.Slf4j;
import net.ssehub.jacat.api.addon.Addon;
import net.ssehub.jacat.api.analysis.IAnalysisCapabilities;
import net.ssehub.jacat.api.addon.task.AbstractAnalysisCapability;
import net.ssehub.jacat.api.addon.task.PreparedTask;
import net.ssehub.jacat.api.addon.task.Task;
import net.ssehub.jacat.api.analysis.IAnalysisScheduler;
import net.ssehub.jacat.api.analysis.IAnalysisTaskExecutor;
import net.ssehub.jacat.api.analysis.TaskCompletion;
import net.ssehub.jacat.worker.analysis.TaskPreparer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@EnableAsync
@Slf4j
public class AnalysisTaskExecutor implements IAnalysisTaskExecutor {

    public static final int MAX_RUNNING_PROCESSES = 5;

    private final IAnalysisCapabilities<Addon> capabilities;
    private final IAnalysisScheduler analysisScheduler;
    private final TaskPreparer taskPreparer;

    private Set<Task> runningTasks = Collections.synchronizedSet(new HashSet<>());

    public AnalysisTaskExecutor(IAnalysisCapabilities<Addon> capabilities,
                                IAnalysisScheduler analysisScheduler,
                                TaskPreparer taskPreparer) {
        this.capabilities = capabilities;
        this.analysisScheduler = analysisScheduler;
        this.taskPreparer = taskPreparer;
    }

    @Scheduled(cron = "*/1 * * * * *")
    private void executeTasks() {
        if (this.runningTasks.size() >= MAX_RUNNING_PROCESSES) {
            return;
        }

        Task task = this.analysisScheduler.getNext();
        if (task != null) {
            this.runningTasks.add(task);
            this.process(task, (result -> {
                this.runningTasks.remove(task);
                result.finish();
            }));
        }
    }

    @Override
    public boolean isRunning(Task task) {
        return this.runningTasks.contains(task);
    }

    @Async
    public void process(Task task, TaskCompletion completion) {
        AbstractAnalysisCapability capability = this.capabilities.getCapability(task.getSlug(), task.getLanguage());

        this.log.info("Started AnalysingTask (#" + task.getId() + "): [slug=\"" + task.getSlug()
                + "\", language=\"" + task.getLanguage() + "\"]");
        long timeStart = System.currentTimeMillis();

        PreparedTask result = new PreparedTask(task);
        try {
            PreparedTask preparedTask = this.taskPreparer.prepare(task);
            result = capability.run(preparedTask);
        } catch(Exception e) {
            result.setFailedResult(Collections.singletonMap("message", e.getMessage()));
        }

        Task.Status status = result.getStatus();
        if (status == null) {
            status = Task.Status.SUCCESSFUL;
        }

        task.setResult(status, result.getResult());
        long timeEnd = System.currentTimeMillis();
        long time = timeEnd - timeStart;

        log.info("Finished AnalysingTask (#" + result.getId() + ") in "
                + time + "ms with status [" + status + "]");
        completion.finish(task);
    }

}
