export interface Entity {
    id?: number;
    name: string;
}

export interface EntityList<Entity> {
    _embedded: {
    };
    _links: {
        self: { href: string},
    }
}