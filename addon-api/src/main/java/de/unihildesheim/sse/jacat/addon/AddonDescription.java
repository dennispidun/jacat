package de.unihildesheim.sse.jacat.addon;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddonDescription that = (AddonDescription) o;
        return mainClass.equals(that.mainClass) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainClass, name);
    }
}
