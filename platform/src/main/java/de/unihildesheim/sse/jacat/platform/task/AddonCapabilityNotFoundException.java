package de.unihildesheim.sse.jacat.platform.task;

import de.unihildesheim.sse.jacat.platform.error.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class AddonCapabilityNotFoundException extends ApplicationRuntimeException {
    public AddonCapabilityNotFoundException(String slug, String language) {
        super("The desired capability (slug=\"" +
                slug + "\", language=\"" + language +
                "\" could not be found.", HttpStatus.NOT_FOUND);
    }
}
