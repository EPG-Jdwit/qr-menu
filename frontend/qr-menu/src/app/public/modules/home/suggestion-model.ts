export interface Suggestion {
    url: string;
    alt: string;
    description: string;
}

export interface SuggestionList<Suggestion> {
    suggestions: Suggestion[];
}