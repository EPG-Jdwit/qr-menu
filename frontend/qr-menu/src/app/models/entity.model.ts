export interface Entity {
    id?: number;
    name: string;
}

export interface EntityList<Entity> {
    _links: {
        self: string;
    },
    _embedded: {
    }
}