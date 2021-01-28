package net.ssehub.jacat.worker.analysis.queue;

import net.ssehub.jacat.api.addon.task.Task;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@EnableScheduling
public class AnalysisTaskScheduler {

    public static final int QUEUE_CAPACITY = 10;
    public static final int MAX_RUNNING_PROCESSES = 5;

    private Queue<Task> queue;
    private AnalysisTaskExecutor analysisTaskExecutor;

    private Set<Task> runningTasks = Collections.synchronizedSet(new HashSet<>());

    public AnalysisTaskScheduler(AnalysisTaskExecutor analysisTaskExecutor) {
        this.queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.analysisTaskExecutor = analysisTaskExecutor;
    }

    public boolean isQueued(Task task) {
        return this.queue.contains(task);
    }

    public boolean isRunning(Task task) {
        return this.runningTasks.contains(task);
    }

    public boolean trySchedule(Task task) {
        if (this.isQueued(task)) {
            return false;
        }

        return this.queue.offer(task);
    }

    public boolean canSchedule() {
        return this.queue.size() < QUEUE_CAPACITY;
    }

    @Scheduled(cron = "*/1 * * * * *")
    private void executeTasks() {
        if (this.runningTasks.size() >= MAX_RUNNING_PROCESSES) {
            return;
        }

        Task task = this.queue.poll();
        if (task != null) {
            this.runningTasks.add(task);
            this.analysisTaskExecutor.process(task, (result -> {
                this.runningTasks.remove(task);
                result.finish();
            }));
        }
    }
}
