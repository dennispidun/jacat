package de.unihildesheim.sse.jacat.addon;


public class AddonDescription {

    private String mainClass;
    private String name;

    public AddonDescription(String mainClass, String name) {
        this.mainClass = mainClass;
        this.name = name;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getName() {
        return name;
    }
}
