package de.unihildesheim.sse.jacatplatform.addon;

import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavaAddonLoader {

    private List<AddonClassLoader> addonClassLoaders = new ArrayList<>();

    public void loadAddon(File file) {
        if (file == null) {
            return;
        }

        try {
            addonClassLoaders.add(new AddonClassLoader(this.getClass().getClassLoader(), file));
        } catch (MalformedURLException e) {
            System.err.println("Could not load addon: " + file.getName());
        }
    }

}
