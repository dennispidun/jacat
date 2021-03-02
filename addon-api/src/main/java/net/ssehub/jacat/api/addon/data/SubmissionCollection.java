package net.ssehub.jacat.api.addon.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SubmissionCollection implements Iterable<Submission> {

    private Set<Submission> submissions;

    public SubmissionCollection() {
        this.submissions = new HashSet<>();
    }

    public void add(Submission submission) {
        this.submissions.add(submission);
    }

    @Override
    public Iterator<Submission> iterator() {
        return this.submissions.iterator();
    }

    public void accept(DataCollectionVisitor visitor) {
        this.forEach((submission -> submission.accept(visitor)));
    }

}
