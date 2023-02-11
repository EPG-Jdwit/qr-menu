package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.SubcategoryAssembler;
import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
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

/**
 * Endpoints for subcategories
 */
@RestController
public class SubcategoryController extends BaseController {

    private final SubcategoryService subcategoryService;

    private final SubcategoryAssembler subcategoryAssembler;

    /**
     * Constructor.
     * @param subcategoryService : A SubcategoryService.
     * @param subcategoryAssembler : A SubcategoryAssembler to create EntityModels of subcategories.
     */
    public SubcategoryController(SubcategoryService subcategoryService, SubcategoryAssembler subcategoryAssembler) {
        this.subcategoryService = subcategoryService;
        this.subcategoryAssembler = subcategoryAssembler;
    }

    /**
     * Retrieves all subcategories of a category with a given ID.
     * @param categoryId : The ID of the category of which the subcategories are to be retrived.
     * @return : A CollectionModel of all the found subcategories
     * @throws NotFoundException : When no category with the given ID was found.
     */
    @GetMapping("/categories/{categoryId}/subcategories")
    public CollectionModel<EntityModel<Subcategory>> getAllCategorySubcategories(@PathVariable Long categoryId) throws NotFoundException {
        return subcategoryAssembler.toCollectionModel(subcategoryService.getAllCategorySubcategories(categoryId), categoryId);
    }

    /**
     * Retrieve a subcategory with a specific ID of a category with categoryId as ID.
     * @param categoryId : The ID of the category of which the subcategory must be retrieved.
     * @param subcategoryId : The ID of the subcategory which is to be retrieved.
     * @return : An EntityModel of the subcategory with the given ID if found.
     * @throws NotFoundException : When either a category or subcategory which provided IDs aren't found.
     */
    @GetMapping("/categories/{categoryId}/subcategories/{subcategoryId}")
    public EntityModel<Subcategory> getCategorySubcategoryById(@PathVariable Long categoryId, @PathVariable Long subcategoryId) throws NotFoundException {
        return subcategoryAssembler.toModel(subcategoryService.getSubcategoryById(categoryId, subcategoryId));
    }

    /**
     * Create a new subcategory for a category with given ID.
     * @param categoryId : The ID of the category for which a subcategory must be created.
     * @param newSubcategory : An object with the data to create a new subcategory.
     * @return : An EntityModel of the created subcategory.
     * @throws NotFoundException : When the category with given ID wasn't found.
     * @throws InputException : When a required field of the subcategory wasn't provided.
     * @throws AlreadyExistsException : When a subcategory with the same name already exists within the same category.
     */
    @PostMapping("/categories/{categoryId}/subcategories")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityModel<Subcategory> addSubcategoryToCategory(@PathVariable Long categoryId, @RequestBody NewSubcategory newSubcategory) throws NotFoundException, InputException, AlreadyExistsException {
        return subcategoryAssembler.toModel(subcategoryService.createNewSubcategory(categoryId, newSubcategory));
    }

    /**
     * Deletes a subcategory of a category with the given ID's.
     * @param categoryId : The ID of the category of which the subcategory must be deleted.
     * @param subcategoryId : The ID of the subcategory to be deleted.
     * @throws NotFoundException : When either a category or subcategory which provided IDs aren't found.
     */
    @DeleteMapping("/categories/{categoryId}/subcategories/{subcategoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSubcategoryById(@PathVariable Long categoryId, @PathVariable Long subcategoryId) throws NotFoundException {
        subcategoryService.deleteSubcategoryById(categoryId, subcategoryId);
    }

    /**
     * Update the data of a product with a specific ID. Fields that weren't provided remain the same.
     *
     * @param categoryId : The ID of the category of which the subcategory must be patched.
     * @param subcategoryId : The ID of the subcategory to be updated.
     * @param newSubcategory : An Object with data of the subcategory to be changed.
     * @return : An EntityModel of the changed subcategory.
     * @throws NotFoundException : When either a category or subcategory which provided IDs aren't found.
     * @throws AlreadyExistsException : When a subcategory of the given category already exists with the same name.
     */
    @PatchMapping("/categories/{categoryId}/subcategories/{subcategoryId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EntityModel<Subcategory> updateSubcategoryById(
            @PathVariable Long categoryId,
            @PathVariable Long subcategoryId,
            @RequestBody NewSubcategory newSubcategory
    ) throws NotFoundException, AlreadyExistsException {
        return subcategoryAssembler.toModel(subcategoryService.patchSubcategoryById(categoryId, subcategoryId, newSubcategory ));
    }
}
