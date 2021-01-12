package de.unihildesheim.sse.jacatplatform.tools;

import de.unihildesheim.sse.jacatplatform.error.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class ToolAlreadyRegisteredException extends ApplicationRuntimeException {
    public ToolAlreadyRegisteredException(Tool tool) {
        super("Tool already exists.", HttpStatus.CONFLICT);
    }
}
