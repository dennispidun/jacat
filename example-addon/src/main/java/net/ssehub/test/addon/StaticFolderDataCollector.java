package net.ssehub.test.addon;

import net.ssehub.jacat.api.addon.data.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;

public class StaticFolderDataCollector extends AbstractDataCollector {

    public StaticFolderDataCollector() {
        super("local");
    }

    @Override
    public SubmissionCollection collect(File base, DataSection configuration) {
        SubmissionCollection submissions = new SubmissionCollection();
        if (configuration.getCourse() != null
                && configuration.getHomework() == null
                && configuration.getSubmission() == null) { // get all submissions for all homeworks
            File baseCopyFrom = new File("C:\\Users\\Dennis\\Desktop\\examplejavacourse");
            Stream<File> stream = Arrays.stream(baseCopyFrom.listFiles());

            stream.forEach(homework -> {
                File[] submissionFiles = homework.listFiles();
                for(File submissionFolder : submissionFiles) {
                    if (submissionFolder.isFile()) {
                        continue;
                    }

                    Path target = base.toPath().resolve(homework.getName()).resolve(submissionFolder.getName());
                    try {
                        FolderUtils.copyFolder(submissionFolder.toPath(), target);
                        submissions.add(new Submission("java", homework.getName(), target.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return submissions;
    }

}
