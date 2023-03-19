import { Entity, EntityList } from "./entity.model";

export interface Suggestion extends Entity {
    url: string;
    name: string;
    description: string;
}

export interface SuggestionList<Suggestion> extends EntityList<Suggestion> {
    suggestions: Suggestion[];
}