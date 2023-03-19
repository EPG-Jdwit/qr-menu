package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


/**
 * An Entity representing a Suggestion.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "suggestions",
        schema = "public",
        indexes = {
                @Index(
                        name = "suggestion_order_nr_index",
                        columnList = "order_nr"
                )
        }
)
public class SuggestionEntity {

    @Id
    @GeneratedValue(generator = "sug_generator")
    @GenericGenerator(
            name = "sug_generator",
            strategy = "com.joshua.qrmenu.repositories.util.IdGenerator"
    )
    @Column(
            name = "suggestion_id",
            updatable = false
    )
    private Long suggestionId;

    @Basic(optional = false)
    @Column(
            name= "url",
            nullable = false
    )
    private String url;

    @Basic(optional = false)
    @Column(
            name= "name",
            nullable = false
    )
    private String name;

    @Basic(optional = false)
    @Column(
            name= "description",
            nullable = false
    )
    private String description;


    @Basic(optional = false)
    @Column(
            name = "order_nr",
            nullable = false
    )
    private int orderNr;
}
