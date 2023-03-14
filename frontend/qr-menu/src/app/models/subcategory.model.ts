import { Category } from "./category.model";
import { Entity, EntityList } from "./entity.model";

export interface Subcategory extends Entity {
    category: Category;
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