package de.unihildesheim.sse.jacatplatform.tools;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private boolean failedAvailabilityTest = false;

    public List<String> getLanguages() {
        return this.languages.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

}
