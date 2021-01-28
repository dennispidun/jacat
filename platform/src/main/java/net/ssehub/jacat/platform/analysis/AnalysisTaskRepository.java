package net.ssehub.jacat.platform.analysis;

import net.ssehub.jacat.api.addon.task.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisTaskRepository extends MongoRepository<AnalysisTask, String> {

    List<AnalysisTask> findAllByStatus(Task.Status status);

}
