package de.unihildesheim.sse.jacatplatform.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListToolDto {

    private String uuid;

    private String name;

    private String type;

    private List<String> languages;

    private boolean failedAvailabilityTest = false;

    public ListToolDto(Tool tool) {
        this.uuid = tool.getUuid();
        this.name = tool.getName();
        this.type = tool.getType();
        this.languages = tool.getLanguages();
        this.failedAvailabilityTest = tool.isFailedAvailabilityTest();
    }
}
