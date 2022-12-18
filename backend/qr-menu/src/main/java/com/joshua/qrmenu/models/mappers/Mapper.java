package com.joshua.qrmenu.models.mappers;

import org.springframework.data.crossstore.ChangeSetPersister;

public interface Mapper<T1, T2, T3> {

    T1 newJsonToEntity(T3 newJson) throws ChangeSetPersister.NotFoundException;

    T2 entityToJson(T1 entity);
}
