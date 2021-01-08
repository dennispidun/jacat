package de.unihildesheim.sse.jacatplatform.tools;

public class ToolAlreadyRegisteredException extends RuntimeException {
    public ToolAlreadyRegisteredException(Tool tool) {
        super("Tool does already exist.");
    }
}
