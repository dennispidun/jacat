package net.ssehub.jacat.api.addon.data;

import java.util.*;

public class SubmissionCollection implements Iterable<Submission> {

    private List<Submission> submissions;

    public SubmissionCollection() {
        this.submissions = new ArrayList<>();
    }

    public void add(Submission submission) {
        this.submissions.add(submission);
    }

    @Override
    public Iterator<Submission> iterator() {
        return this.submissions.iterator();
    }

    public void accept(SubmissionMoverVisitor visitor) {
        for(Submission submission : this.submissions) {
            try {
                submission.accept(visitor);
            } catch (RuntimeException e) {
                submissions.remove(submission);
                e.printStackTrace();
            }
        }
    }
}
