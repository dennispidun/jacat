package de.unihildesheim.sse.jacat.platform.addon;

public class AnalysisCapabilityAlreadyRegisteredException extends RuntimeException {
    public AnalysisCapabilityAlreadyRegisteredException(String slug, String language) {
        super("The desired capability (slug=\"" +
                slug + "\", language=\"" + language +
                "\") is already registered.");
    }
}
