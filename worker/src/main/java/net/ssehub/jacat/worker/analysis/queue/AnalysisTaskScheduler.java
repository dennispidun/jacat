package net.ssehub.jacat.worker.analysis.queue;

import net.ssehub.jacat.worker.analysis.AnalysisTask;
import net.ssehub.jacat.worker.analysis.AnalysisTaskRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class AnalysisTaskScheduler {

    private TaskQueue queue;
    private AnalysisTaskRepository taskRepository;
    private AnalysisTaskExecutor analysisTaskExecutor;

    public AnalysisTaskScheduler(TaskQueue queue,
                                 AnalysisTaskRepository taskRepository,
                                 AnalysisTaskExecutor analysisTaskExecutor) {
        this.queue = queue;
        this.taskRepository = taskRepository;
        this.analysisTaskExecutor = analysisTaskExecutor;
    }

    @Scheduled(cron = "*/5 * * * * *")
    private void scheduleTasks() {
        List<AnalysisTask> tasks = this.taskRepository.findAllByStatus(AnalysisTask.Status.QUEUED);
        tasks.stream().limit(10).forEach(this.queue::queue);
    }

    @Scheduled(cron = "*/1 * * * * *")
    private void executeTasks() {
        AnalysisTask task = this.queue.poll();
        if (task != null) {
            this.analysisTaskExecutor.process(task);
        }
    }





}
