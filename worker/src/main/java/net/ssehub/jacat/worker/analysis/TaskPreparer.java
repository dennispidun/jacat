package net.ssehub.jacat.worker.analysis;

import net.ssehub.jacat.api.addon.data.AbstractDataCollector;
import net.ssehub.jacat.api.addon.data.DataSection;
import net.ssehub.jacat.api.addon.data.SubmissionCollection;
import net.ssehub.jacat.api.addon.task.PreparedTask;
import net.ssehub.jacat.api.addon.task.Task;
import net.ssehub.jacat.worker.data.DataCollectors;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TaskPreparer {

    private DataCollectors dataCollectors;

    public TaskPreparer(DataCollectors dataCollectors) {
        this.dataCollectors = dataCollectors;
    }

    public PreparedTask prepare(Task task) {
        DataSection data = task.getDataConfiguration();
        PreparedTask preparedTask = new PreparedTask(task);
        if (data != null) {
            AbstractDataCollector collector = this.dataCollectors.getCollector(data.getProtocol());
            File workspace = new File(new File(".", "debug"), "workspace");
            File base = new File(workspace, "tmp_" + task.getId());
            base.mkdirs();

            SubmissionCollection collection = collector.collect(base, data);
            // TODO: Check if created Files are in Folder
            preparedTask.setSubmissions(collection);
        }

        return preparedTask;
    }

}
