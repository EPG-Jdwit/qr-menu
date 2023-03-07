export interface Product {
    id: number;
    name: string;
    price: number;
    description: string;
    allergenics: string[];
    _links: {
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

export interface ProductList<Product> {
    _embedded: {
        productList: Product[]
    };
    _links: {
        self: { href: string}
    }
}