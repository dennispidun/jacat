package de.unihildesheim.sse.jacatplatform.tools;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface IToolRepository extends CrudRepository<Tool, String> {

    boolean existsToolByApiBaseUrl(String apiBaseUrl);

    List<Tool> findAllByLanguagesContaining(String language);
}
