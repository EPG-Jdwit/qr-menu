package com.joshua.qrmenu.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "meta_data",
        schema = "public"
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "meta_data_key_unique",
//                        columnNames = {"key"}
//                )
//        }
)
public class MetadataEntity   {

//    private static final int MAX_KEY_LENGTH = 1023;
//    private static final int MAX_VALUE_LENGTH = 65535;

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
//            length = MAX_KEY_LENGTH
    )
    private String key;

    @Basic(optional = false)
    @Column(
            name = "meta_value",
            nullable = false
//            length = MAX_VALUE_LENGTH
    )
    private String value;

    public MetadataEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
