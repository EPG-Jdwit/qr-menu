package com.joshua.qrmenu.root;

import com.joshua.qrmenu.endpoints.assemblers.RootAssembler;
import com.joshua.qrmenu.models.json.Root;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RootAssemblerTest {

    private static final RootAssembler rootAssembler = new RootAssembler();

    @Test
    public void testRoot() {
        Root root = new Root();
        EntityModel<Root> result = rootAssembler.toModel(root);
        assertThat(result.getLink("self").isPresent()).isTrue();
        assertThat(result.getLink("categories").isPresent()).isTrue();
        assertThat(result.getLink("products").isPresent()).isTrue();
    }
}
