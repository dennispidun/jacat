package net.ssehub.jacat.api.addon.data;

public interface SubmissionMoverVisitor {
    void visit(Submission submission) throws RuntimeException;
}
