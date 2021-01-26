package net.ssehub.jacat.worker.analysis.queue;

import net.ssehub.jacat.worker.analysis.AnalysisTask;
import net.ssehub.jacat.worker.analysis.AnalysisTaskRepository;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class TaskQueue {

    private AnalysisTaskRepository taskRepository;
    private Queue<String> innerQueue = new LinkedList<>();

    public TaskQueue(AnalysisTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void queue(AnalysisTask analysisTask) {
        analysisTask.setStatus(AnalysisTask.Status.QUEUED);
        analysisTask = this.taskRepository.save(analysisTask);
        this.innerQueue.add(analysisTask.getId());
    }

    public AnalysisTask poll() {
        if (this.innerQueue.size() == 0) {
            return null;
        }

        String polledId = innerQueue.poll();
        return this.taskRepository.findById(polledId).orElse(null);
    }



}
