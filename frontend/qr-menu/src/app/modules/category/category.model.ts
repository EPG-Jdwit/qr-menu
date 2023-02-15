export interface Category {
    id: number;
    name: string;
    _links: {
        self: { href: string},
        subcategories:{ href: string},
        categories:{ href: string}
    };
}

export interface CategoryList<Category> {
    _embedded: {
        categoryList: Category[]
    }
    _links: {
        self: string;
    }
}