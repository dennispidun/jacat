package de.unihildesheim.sse.jacatplatform.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    private String uuid;

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    @NotEmpty
    private List<String> languages;

    @NotEmpty
    private String apiBaseUrl;

}
