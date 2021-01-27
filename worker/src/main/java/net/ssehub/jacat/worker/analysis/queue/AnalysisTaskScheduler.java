package net.ssehub.jacat.worker.analysis.queue;

import net.ssehub.jacat.api.addon.task.Task;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@EnableScheduling
public class AnalysisTaskScheduler {

    public static final int QUEUE_CAPACITY = 10;

    private Queue<Task> queue;
    private AnalysisTaskExecutor analysisTaskExecutor;

    private AtomicInteger currentProcesses = new AtomicInteger(0);

    public AnalysisTaskScheduler(AnalysisTaskExecutor analysisTaskExecutor) {
        this.queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.analysisTaskExecutor = analysisTaskExecutor;
    }

    public boolean trySchedule(Task task) {
        return this.queue.offer(task);
    }

    public boolean canSchedule() {
        return this.queue.size() < QUEUE_CAPACITY;
    }

    @Scheduled(cron = "*/1 * * * * *")
    private void executeTasks() {
        if (this.currentProcesses.get() >= 5) {
            return;
        }

        Task task = this.queue.poll();
        if (task != null) {
            this.currentProcesses.incrementAndGet();
            this.analysisTaskExecutor.process(task, (result -> {
                this.currentProcesses.decrementAndGet();
                result.finish();
            }));
        }
    }
}
