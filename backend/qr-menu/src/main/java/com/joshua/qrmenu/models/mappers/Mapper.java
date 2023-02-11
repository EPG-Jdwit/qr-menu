package com.joshua.qrmenu.models.mappers;

/**
 * Interface for implementing Mappers
 * @param <T1> : An Entity class.
 * @param <T2> : A New Data Transfer Object class.
 * @param <T3> : A Data Transfer Object class.
 */
public interface Mapper<T1, T2, T3> {

    T1 newJsonToEntity(T3 newJson);

    T2 entityToJson(T1 entity);
}
