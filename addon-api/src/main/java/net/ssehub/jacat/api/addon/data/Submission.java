package net.ssehub.jacat.api.addon.data;

import java.util.Objects;

public class Submission {

    private String course;
    private String homework;
    private String basePath;

    public Submission(String course, String homework, String basePath) {
        this.course = course;
        this.homework = homework;
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
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
}
