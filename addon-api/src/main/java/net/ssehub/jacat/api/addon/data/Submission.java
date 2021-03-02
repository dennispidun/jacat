package net.ssehub.jacat.api.addon.data;

import java.nio.file.Path;
import java.util.Objects;

public class Submission {

    private String course;
    private String homework;
    private Path basePath;

    public Submission(String course, String homework, Path basePath) {
        this.course = course;
        this.homework = homework;
        this.basePath = basePath;
    }

    public Path getBasePath() {
        return basePath;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return Objects.equals(course, that.course)
                && Objects.equals(homework, that.homework)
                && Objects.equals(basePath, that.basePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, homework, basePath);
    }

    public void accept(DataCollectionVisitor visitor) {
        visitor.visit(this);
    }
}
