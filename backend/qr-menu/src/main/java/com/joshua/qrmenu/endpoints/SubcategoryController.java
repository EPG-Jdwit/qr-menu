package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.SubcategoryAssembler;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.models.json.Subcategory;
import com.joshua.qrmenu.services.SubcategoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubcategoryController extends BaseController {

    private final SubcategoryService subcategoryService;

    private final SubcategoryAssembler subcategoryAssembler;

    public SubcategoryController(SubcategoryService subcategoryService, SubcategoryAssembler subcategoryAssembler) {
        this.subcategoryService = subcategoryService;
        this.subcategoryAssembler = subcategoryAssembler;
    }

    @GetMapping("/categories/{categoryId}/subcategories")
    public CollectionModel<EntityModel<Subcategory>> getAllCategorySubcategories(@PathVariable Long categoryId) throws NotFoundException {
        return subcategoryAssembler.toCollectionModel(subcategoryService.getAllCategorySubcategories(categoryId));
    }

    @GetMapping("/categories/{categoryId}/subcategories/{subcategoryId}")
    public EntityModel<Subcategory> getCategorySubcategoryById(@PathVariable Long categoryId, @PathVariable Long subcategoryId) throws NotFoundException {
        return subcategoryAssembler.toModel(subcategoryService.getSubcategoryById(categoryId, subcategoryId));
    }

    @PostMapping("/categories/{categoryId}/subcategories")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityModel<Subcategory> addSubcategoryToCategory(@PathVariable Long categoryId, @RequestBody NewSubcategory newSubcategory) throws NotFoundException {
        return subcategoryAssembler.toModel(subcategoryService.createNewSubcategory(categoryId, newSubcategory));
    }

    @DeleteMapping("/categories/{categoryId}/subcategories/{subcategoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSubcategoryById(@PathVariable Long categoryId, @PathVariable Long subcategoryId) throws NotFoundException {
        subcategoryService.deleteSubcategoryById(categoryId, subcategoryId);
    }

    @PatchMapping("/categories/{categoryId}/subcategories/{subcategoryId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EntityModel<Subcategory> updateSubcategoryById(
            @PathVariable Long categoryId,
            @PathVariable Long subcategoryId,
            @RequestBody NewSubcategory newSubcategory
    ) throws NotFoundException {
        return subcategoryAssembler.toModel(subcategoryService.patchSubcategoryById(categoryId, subcategoryId, newSubcategory ));
    }
}
