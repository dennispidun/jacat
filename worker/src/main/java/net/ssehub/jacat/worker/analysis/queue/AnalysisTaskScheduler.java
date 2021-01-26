package net.ssehub.jacat.worker.analysis.queue;

import net.ssehub.jacat.worker.analysis.AnalysisTask;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@EnableScheduling
public class AnalysisTaskScheduler {

    private Queue<AnalysisTask> queue;
    private AnalysisTaskExecutor analysisTaskExecutor;

    private AtomicInteger currentProcesses = new AtomicInteger(0);

    public AnalysisTaskScheduler(AnalysisTaskExecutor analysisTaskExecutor) {
        this.queue = new ArrayBlockingQueue<>(10);
        this.analysisTaskExecutor = analysisTaskExecutor;
    }

    public boolean trySchedule(AnalysisTask task) {
        return this.queue.offer(task);
    }

    @Scheduled(cron = "*/1 * * * * *")
    private void executeTasks() {
        if (this.currentProcesses.get() >= 5) {
            return;
        }

        AnalysisTask task = this.queue.poll();
        if (task != null) {
            this.currentProcesses.incrementAndGet();
            this.analysisTaskExecutor.process(task, () -> this.currentProcesses.decrementAndGet());
        }
    }

    protected static interface FinishCallback {
        void finish();
    }
}
