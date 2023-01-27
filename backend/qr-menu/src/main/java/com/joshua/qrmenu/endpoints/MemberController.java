package com.joshua.qrmenu.endpoints;

import com.joshua.qrmenu.endpoints.assemblers.CategoryAssembler;
import com.joshua.qrmenu.endpoints.assemblers.ProductAssembler;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.endpoints.util.BaseController;
import com.joshua.qrmenu.models.json.Category;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.MemberService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController extends BaseController {

        private final MemberService memberService;
        private final ProductAssembler productAssembler;
        private final CategoryAssembler categoryAssembler;

        public MemberController(MemberService memberService, ProductAssembler productAssembler, CategoryAssembler categoryAssembler) {
            this.memberService = memberService;
            this.productAssembler = productAssembler;
            this.categoryAssembler = categoryAssembler;
        }

//        @GetMapping("/categories/{categoryId}/categoryProducts")
//        public CollectionModel<EntityModel<Product>> getCategoryProducts(@PathVariable Long categoryId) throws NotFoundException {
//            return productAssembler.toCollectionModel(memberService.getCategoryProducts(categoryId));
//        }
//
//        @GetMapping("/products/{productId}/productCategories")
//        public CollectionModel<EntityModel<Category>> getProductCategories(@PathVariable Long productId) throws NotFoundException {
//            return categoryAssembler.toCollectionModel(memberService.getProductCategories(productId));
//        }

}
