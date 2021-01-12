package de.unihildesheim.sse.jacatplatform.tools;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryToolRepository implements IToolRepository {

    private Map<String, Tool> tools = new HashMap<>();

    @Override
    public <S extends Tool> S save(S entity) {
        tools.put(entity.getUuid(), entity);
        return entity;
    }

    @Override
    public <S extends Tool> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Tool> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Tool> findAll() {
        return new ArrayList<>(tools.values());
    }

    @Override
    public Iterable<Tool> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Tool entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Tool> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsToolByApiBaseUrl(String apiBaseUrl) {
        return this.tools.values().stream()
                .anyMatch(tool -> tool.getApiBaseUrl().equalsIgnoreCase(apiBaseUrl));
    }

    @Override
    public List<Tool> findAllByLanguagesContaining(String language) {
        return this.tools.values().stream()
                .filter(tool -> tool.getLanguages().contains(language.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tool> findAllByType(String type) {
        return this.tools.values().stream()
                .filter(tool -> tool.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tool> findAllByTypeAndLanguagesContaining(String type, String language) {
        return this.tools.values().stream()
                .filter(tool -> tool.getType().equalsIgnoreCase(type)
                        && tool.getLanguages().contains(language.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllLanguagesByType(String type) {
        return this.findAllByType(type).stream()
                .flatMap(tool -> tool.getLanguages().stream())
                .distinct().collect(Collectors.toList());
    }
}
