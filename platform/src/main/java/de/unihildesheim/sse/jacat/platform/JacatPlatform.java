package de.unihildesheim.sse.jacat.platform;

import de.unihildesheim.sse.jacat.AbstractJacatPlatform;
import org.springframework.stereotype.Service;

@Service
public class JacatPlatform extends AbstractJacatPlatform {

    public String getVersion() {
        return "1.2.3";
    }

}
