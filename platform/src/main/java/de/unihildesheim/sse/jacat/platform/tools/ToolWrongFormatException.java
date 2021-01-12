package de.unihildesheim.sse.jacat.platform.tools;

import de.unihildesheim.sse.jacat.platform.error.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class ToolWrongFormatException extends ApplicationRuntimeException {
    public ToolWrongFormatException(Tool tool) {
        super("Tool does not meet specification.", HttpStatus.BAD_REQUEST);
    }
}
