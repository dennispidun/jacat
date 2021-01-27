package net.ssehub.jacat.platform.analysis;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisTaskRepository extends MongoRepository<AnalysisTask, String> {
}
