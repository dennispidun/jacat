package net.ssehub.jacat.api.addon.data;

import java.io.File;

public abstract class AbstractDataCollector {

    private String protocol;

    public AbstractDataCollector(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public abstract SubmissionCollection collect(File base, DataSection configuration);
}
