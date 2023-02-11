package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.CategoryAssembler;
import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints for categories
 */
@RestController
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    private final CategoryAssembler categoryAssembler;

    /**
     * Constructor.
     *
     * @param categoryService: A CategoryService.
     * @param categoryAssembler : A CategoryAssembler to create EntityModels of Categories.
     */
    public CategoryController(CategoryService categoryService, CategoryAssembler categoryAssembler) {
        this.categoryService = categoryService;
        this.categoryAssembler = categoryAssembler;
    }

    /**
     * Retrieve all categories.
     *
     * @return : A CollectionModel of the list of all existing categories.
     */
    @GetMapping("/categories")
    public CollectionModel<EntityModel<Category>> getAllCategories() {
        return categoryAssembler.toCollectionModel(
                categoryService.getAll()
        );
    }

    /**
     * Retrieve a category with the given ID.
     *
     * @param categoryId : The ID of the category to be retrieved.
     * @return : An EntityModel of the category with the given ID if found.
     * @throws NotFoundException : When the ID doesn't correspond to an existing category.
     */
    @GetMapping("/categories/{categoryId}")
    public EntityModel<Category> getCategoryById(@PathVariable Long categoryId) throws NotFoundException {
        return categoryAssembler.toModel(categoryService.getCategoryById(categoryId));
    }

    //TODO: producs get added to category, but another category gets created with diff ID

    /**
     * Retrieves a category with a given name.
     *
     * @param name : The name of the category to be retrieved.
     * @return : An EntityModel of the category with given name if found.
     * @throws NotFoundException : If no category with the given name is found.
     */
    @GetMapping("/categories/category")
    public EntityModel<Category> getCategoryByName(@RequestParam String name) throws NotFoundException {
        return categoryAssembler.toModel(categoryService.getCategoryByName(name));
    }

    /**
     * Creates a new category.
     *
     * @param newCategory : An Object with the data to create a new Category.
     * @return : An EntityModel of the created category.
     * @throws InputException : When a required field of the category wasn't provided.
     * @throws AlreadyExistsException: If a category already exists with the same name.
     */
    @PostMapping("/categories")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityModel<Category> addCategory(@RequestBody NewCategory newCategory) throws InputException, AlreadyExistsException {
        return categoryAssembler.toModel(categoryService.createNewCategory(newCategory));
    }

    /**
     * Deletes a category by ID.
     *
     * @param categoryId : The ID of the category to be deleted.
     * @throws NotFoundException : If no category with the given ID was found.
     */
    @DeleteMapping("/categories/{categoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long categoryId) throws NotFoundException {
        categoryService.deleteCategoryById(categoryId);
    }

    /**
     * Update the data of a category with a specific ID. Fields that weren't provided remain the same.
     *
     * @param categoryId : ID of the category to be updated.
     * @param newCategory : An Object with the data to be changed.
     * @return : An EntityModel of the changed category.
     * @throws NotFoundException : When no category with the given ID wasn't found.
     * @throws AlreadyExistsException : When a category already exists with the same name.
     */
    @PatchMapping("/categories/{categoryId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EntityModel<Category> updateCategoryById(@PathVariable Long categoryId, @RequestBody NewCategory newCategory) throws  NotFoundException, AlreadyExistsException {
        Category category = categoryService.patchCategoryById(categoryId, newCategory);
        return categoryAssembler.toModel(category);
    }

}
