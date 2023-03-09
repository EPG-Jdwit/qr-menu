import { Entity, EntityList } from "./entity.model";
import { Subcategory } from "./subcategory.model";

export interface Category extends Entity {
    subcategories: Subcategory[];
    _links: {
        self: { href: string},
        subcategories:{ href: string},
        categories:{ href: string}
    };
}

export interface CategoryList<Category> extends EntityList<Entity> {
    _embedded: {
        categoryList: Category[]
    }
}