package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.CategoryAssembler;
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

@RestController
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    private final CategoryAssembler categoryAssembler;

    public CategoryController(CategoryService categoryService, CategoryAssembler categoryAssembler) {
        this.categoryService = categoryService;
        this.categoryAssembler = categoryAssembler;
    }

    @GetMapping("/categories")
    public CollectionModel<EntityModel<Category>> getAllCategories() {
        return categoryAssembler.toCollectionModel(
                categoryService.getAll()
        );
    }

    @GetMapping("/categories/{categoryId}")
    public EntityModel<Category> getCategoryById(@PathVariable Long categoryId) throws NotFoundException {
        return categoryAssembler.toModel(categoryService.getCategoryById(categoryId));
    }

    //TODO: producs get added to category, but another category gets created with diff ID
    @GetMapping("/categories/category")
    public EntityModel<Category> getCategoryByName(@RequestParam String name) throws NotFoundException {
        return categoryAssembler.toModel(categoryService.getCategoryByName(name));
    }

    @PostMapping("/categories")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EntityModel<Category> addCategory(@RequestBody NewCategory newCategory) throws InputException {
        return categoryAssembler.toModel(categoryService.createNewCategory(newCategory));
    }

    @DeleteMapping("/categories/{categoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long categoryId) throws NotFoundException {
        categoryService.deleteCategoryById(categoryId);
    }

    @PatchMapping("/categories/{categoryId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EntityModel<Category> updateCategoryById(@PathVariable Long categoryId, @RequestBody NewCategory newCategory) throws  NotFoundException {
        Category category = categoryService.patchCategoryById(categoryId, newCategory);
        return categoryAssembler.toModel(category);
    }

}
