package net.ssehub.jacat.api.addon.data;


public class DataSection {

    private String protocol;

    private String course;
    private String homework;
    private String submission;

    public DataSection() {
    }

    public DataSection(String protocol, String course, String homework, String submission) {
        this.protocol = protocol;
        this.course = course;
        this.homework = homework;
        this.submission = submission;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public String getProtocol() {
        return protocol;
    }
}
