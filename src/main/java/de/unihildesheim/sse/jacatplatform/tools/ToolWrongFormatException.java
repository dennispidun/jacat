package de.unihildesheim.sse.jacatplatform.tools;

import de.unihildesheim.sse.jacatplatform.error.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class ToolWrongFormatException extends ApplicationRuntimeException {
    public ToolWrongFormatException(Tool tool) {
        super("Tool does not meet specification.", HttpStatus.BAD_REQUEST);
    }
}
