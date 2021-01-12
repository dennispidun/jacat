package de.unihildesheim.sse.jacat.platform.tools;

import de.unihildesheim.sse.jacat.platform.error.ApplicationRuntimeException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ToolsAvailabilityService {

    private final RestTemplate restTemplate;

    public ToolsAvailabilityService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void checkAvailability(Tool tool) throws ApplicationRuntimeException {
        String healthEndpoint = tool.getApiBaseUrl() + "/health";
        try {
            ResponseEntity<Void> response = this.restTemplate.getForEntity(healthEndpoint, Void.class);
            if(response.getStatusCode() != HttpStatus.OK) {
                throw new ToolWrongFormatException(tool);
            }
        } catch (RestClientException restClientException) {
            throw new ToolIsNotAvailableException(tool);
        }
    }

}
