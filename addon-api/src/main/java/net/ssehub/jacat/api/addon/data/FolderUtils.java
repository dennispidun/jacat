package net.ssehub.jacat.api.addon.data;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;

public class FolderUtils {

    public static void copyFolder(Path workspace, Path source ) throws IOException {
        copyFolder(workspace, source, StandardCopyOption.COPY_ATTRIBUTES);
    }

    public static void copyFolder(Path workspace, Path source, CopyOption ...options)
            throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                createDirectories(workspace.resolve(source.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                copy(file, workspace.resolve(source.relativize(file.getFileName())), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
