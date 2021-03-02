package net.ssehub.test.addon;

import net.ssehub.jacat.api.addon.data.*;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.nio.file.Files.copy;

public class StaticFolderDataCollector extends AbstractDataCollector {

    public StaticFolderDataCollector() {
        super("local");
    }

    @Override
    public void arrange(DataRequest request) {

    }

    @Override
    public SubmissionCollection collect(DataRequest dataRequest) {
        SubmissionCollection submissions = new SubmissionCollection();
        if (dataRequest.getCourse() != null
                && dataRequest.getHomework() == null
                && dataRequest.getSubmission() == null) { // get all submissions for all homeworks
            File baseCopyFrom = new File("C:\\Users\\Dennis\\Desktop\\examplejavacourse");
            Stream<File> stream = Arrays.stream(baseCopyFrom.listFiles());

            stream.forEach(homework -> {
                File[] submissionFiles = homework.listFiles();
                for(File submissionFolder : submissionFiles) {
                    if (submissionFolder.isFile()) {
                        continue;
                    }
                    submissions.add(new Submission("java", homework.getName(), submissionFolder.toPath()));
                }
            });
        }

        return submissions;
    }

}
