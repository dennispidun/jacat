package de.unihildesheim.sse.jacat.platform.tools;

import de.unihildesheim.sse.jacat.platform.error.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class ToolAlreadyRegisteredException extends ApplicationRuntimeException {
    public ToolAlreadyRegisteredException(Tool tool) {
        super("Tool already exists.", HttpStatus.CONFLICT);
    }
}
