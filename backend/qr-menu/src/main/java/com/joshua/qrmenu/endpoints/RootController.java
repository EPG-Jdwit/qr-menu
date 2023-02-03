package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.RootAssembler;
import com.joshua.qrmenu.models.json.Root;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Root endpoint
 */
@RestController
public class RootController {

    private final RootAssembler rootAssembler;

    /**
     * Constructor
     *
     * @param rootAssembler : A RootAssembler to create EntityModels with HATEAOS links.
     */
    public RootController(RootAssembler rootAssembler) {
        this.rootAssembler = rootAssembler;
    }

    /**
     * Retrieves the root with HATEAOS links to all other top-level endpoints.
     * @return : EntityModel of the root
     */
    @GetMapping("/")
    public EntityModel<Root> getAllPaths() {
        return rootAssembler.toModel(new Root());
    }



}
