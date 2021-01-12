package de.unihildesheim.sse.jacat.platform.addon;

import de.unihildesheim.sse.jacat.platform.JacatPlatform;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Component
public class JavaAddonLoader {

    private List<AddonClassLoader> addonClassLoaders = new ArrayList<>();

    public JavaAddonLoader() {
        loadAddonFolder(new File("./debug/addons"));
    }

    public void loadAddonFolder(File folder) {
        if (folder == null || !folder.isDirectory()) {
            return;
        }

        for (File addonFile : folder.listFiles()) {
            if (!addonFile.isFile()) {
                continue;
            }

            AddonDescription addonDescription = getDescription(addonFile);
            if (addonDescription != null) {
                loadAddon(addonFile, addonDescription);
            }
        }
    }

    private boolean isAddonFile(File file) {

        return false;
    }

    public void loadAddon(File file, AddonDescription addonDescription) {
        if (file == null) {
            return;
        }

        try {
            addonClassLoaders.add(new AddonClassLoader(file, addonDescription, new JacatPlatform()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private AddonDescription getDescription(File file) {
        try {
            JarFile jarFile = new JarFile(file);
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                if (entry.getName().equalsIgnoreCase("addon.yml")
                        || entry.getName().equalsIgnoreCase("addon.yaml")) {
                    InputStream input = jarFile.getInputStream(entry);
                    Yaml yaml = new Yaml();
                    Map<String, Object> obj = yaml.load(input);

                    return new AddonDescription((String) obj.get("main"),
                            (List<String>) obj.get("languages"),
                            (String) obj.get("type"));
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

}
