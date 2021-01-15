package de.unihildesheim.sse.jacat.api.addon.task;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Die {@link AbstractAnalysisCapability} beschreibt eine
 * Analysefähigkeit eines Addons. Eine Analysefähigkeit wird
 * immer über einen 'slug' und die Programmiersprachen beschrieben.
 * Mithilfe des Slugs und den Sprachen wird eine Fähigkeit eindeutig
 * identifiziert. Sollte daher bereits eine Analysefähigkeit mit
 * genau diesen Eigenschaften registriert worden sein, verweigert
 * die Plattform das Laden des Addons.
 * Außerdem startet die Plattform mithilfe dieser Klasse eine Analyse.
 */
public abstract class AbstractAnalysisCapability {

    private final String slug;
    private final List<String> languages;

    /**
     * Dies ist der Standardweg, um eine Analysefähigkeit zu erstellen.
     * Es muss immer der Slug und die Sprachen angegeben werden. Diese
     * dürfen nicht null sein und dürfen nicht leer sein. Sowohl Slug
     * als auch die Programmiersprachen werden in LowerCase gespeichert.
     * Hierdurch wird das Auffinden, der Analysefähigkeit durch die
     * Plattform unterstützt.
     *
     * @param slug Der Slug unter der die Analyse gefunden werden soll.
     *             Dieser darf maximal aus einem Wort bestehen und sollte
     *             keine Umlaute oder Zahlen verwenden.
     * @param languages Die Sprachen, welche die Analyse abdecken kann.
     */
    public AbstractAnalysisCapability(String slug, List<String> languages) {
        this.slug = slug;
        this.languages = languages.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    /**
     * Gibt die Programmiersprachen zurück, die von dieser Fähigkeit
     * unterstützt werden.
     *
     * @return Alle Programmiersprachen, die angelegt wurden
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Gibt den Slug zurück unter der die Fähigkeit gefunden werden kann.
     * Ein Slug ist eine Kurzbeschreibung (maximal ein Wort), sozusagen
     * eine Art Kategorie.
     *
     * @return Slug der Analysefähigkeit
     */
    public String getSlug() {
        return slug;
    }
}
