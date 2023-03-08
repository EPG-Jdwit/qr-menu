import { Entity, EntityList } from "./entity.model";

export interface Category extends Entity {
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