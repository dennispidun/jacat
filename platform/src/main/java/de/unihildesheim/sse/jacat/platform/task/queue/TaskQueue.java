package de.unihildesheim.sse.jacat.platform.task.queue;

import de.unihildesheim.sse.jacat.platform.task.AnalysisTask;
import de.unihildesheim.sse.jacat.platform.task.AnalysisTaskRepository;
import de.unihildesheim.sse.jacat.platform.task.TaskJobStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class TaskQueue {

    private AnalysisTaskRepository taskRepository;

    public TaskQueue(AnalysisTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void queue(AnalysisTask analysisTask) {
        if (this.taskRepository.findAllByStatus(TaskJobStatus.QUEUED).size() < 5) {
            analysisTask.setStatus(TaskJobStatus.QUEUED);
            this.taskRepository.save(analysisTask);
        }
    }

    public AnalysisTask poll() {
        List<AnalysisTask> queued = this.taskRepository.findAllByStatus(TaskJobStatus.QUEUED);
        if (queued.size() == 0) {
            return null;
        }

        AnalysisTask analysisTask = queued.get(0);
        return analysisTask;
    }



}
