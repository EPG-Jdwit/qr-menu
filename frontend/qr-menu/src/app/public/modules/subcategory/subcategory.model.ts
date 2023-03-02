export interface Subcategory {
    id: number;
    name: string;
    _links: {
        self: { href: string},
        products:{ href: string},
        subcategories:{ href: string},
        category:{ href: string}
    };
}

export interface SubcategoryList<Subcategory> {
    _embedded: {
        subcategoryList: Subcategory[]
    }
    _links: {
        self: string;
    }
}