package de.unihildesheim.sse.jacat.platform.tools;

import de.unihildesheim.sse.jacat.platform.error.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class ToolIsNotAvailableException extends ApplicationRuntimeException {
    public ToolIsNotAvailableException(Tool tool) {
        super("Tool not found, make sure that it is up and running.", HttpStatus.NOT_FOUND);
    }
}
