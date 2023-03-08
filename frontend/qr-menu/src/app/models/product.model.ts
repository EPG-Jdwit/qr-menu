import { Entity, EntityList } from "./entity.model";

export interface Product extends Entity {
    price?: number;
    description?: string;
    allergenicList?: string[];
    _links?: {
        self: { href: string},
        products:{ href: string}
    };
}

export interface SubcategoryProductList<Product> {
    _embedded: {
        productList: Product[]
    };
    _links: {
        self: { href: string},
        subcategory: { href: string},
        category: { href: string},
    }
}

export interface ProductList<Product> extends EntityList<Entity>{
    _embedded: {
        productList: Product[]
    };
}