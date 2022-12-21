package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.CategoryAssembler;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.services.CategoryService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

    @PostMapping("/categories")
    public EntityModel<Category> addCategory(@RequestBody NewCategory newCategory) {
        return categoryAssembler.toModel(categoryService.createNewCategory(newCategory));
    }

    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategoryById(@PathVariable Long categoryId) throws NotFoundException {
        categoryService.deleteCategoryById(categoryId);
    }

    @PatchMapping("/categories/{categoryId}")
    public EntityModel<Category> patchCategoryById(@PathVariable Long categoryId, @RequestBody NewCategory newCategory) throws  NotFoundException {
        return categoryAssembler.toModel(categoryService.patchCategoryById(categoryId, newCategory));
    }


}