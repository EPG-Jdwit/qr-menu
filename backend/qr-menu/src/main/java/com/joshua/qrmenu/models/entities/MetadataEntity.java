package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * An Entity to guarantee that the database only gets populated once.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "meta_data",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "meta_data_key_unique",
                        columnNames = {"meta_key"}
                )
        }
)
public class MetadataEntity   {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Basic(optional = false)
    @Column(
            name = "meta_key",
            nullable = false
    )
    private String key;

    @Basic(optional = false)
    @Column(
            name = "meta_value",
            nullable = false
    )
    private String value;

    public MetadataEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
