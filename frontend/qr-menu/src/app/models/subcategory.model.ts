import { Category } from "./category.model";
import { Product } from "./product.model";
import { Entity, EntityList } from "./entity.model";

export interface Subcategory extends Entity {
    category: Category;
    categoryId?: number;
    productList?: Product[];
    products?: number[];
    _links?: {
        self: { href: string},
        products:{ href: string},
        subcategories:{ href: string},
        category:{ href: string}
    };
}

export interface SubcategoryList<Subcategory> extends EntityList<Entity>{
    _embedded: {
        subcategoryList: Subcategory[]
    }
}