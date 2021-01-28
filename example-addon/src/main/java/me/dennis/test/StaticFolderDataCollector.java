package me.dennis.test;

import net.ssehub.jacat.api.addon.data.AbstractDataCollector;
import net.ssehub.jacat.api.addon.data.DataSection;
import net.ssehub.jacat.api.addon.data.Submission;
import net.ssehub.jacat.api.addon.data.SubmissionCollection;

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
                        copyFolder(submissionFolder.toPath(), target, StandardCopyOption.COPY_ATTRIBUTES);
                        submissions.add(new Submission("java", homework.getName(), target.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return submissions;
    }

    public void copyFolder(Path source, Path target, CopyOption... options)
            throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                createDirectories(target.resolve(source.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                copy(file, target.resolve(source.relativize(file.getFileName())), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
