package net.ssehub.jacat.worker.data;

import net.ssehub.jacat.api.addon.data.DataCollectionVisitor;
import net.ssehub.jacat.api.addon.data.Submission;
import net.ssehub.jacat.api.addon.data.SubmissionCollection;

import java.nio.file.Path;

public class MoveCollectionVisitor implements DataCollectionVisitor {


    private SubmissionCollection collection;
    private Path target;

    public MoveCollectionVisitor(SubmissionCollection collection, Path target) {
        this.collection = collection;
        this.target = target;
    }

    @Override
    public void visit(Submission submission) {
        // move path virtually in submission
        // move file in FileSystem
    }
}
