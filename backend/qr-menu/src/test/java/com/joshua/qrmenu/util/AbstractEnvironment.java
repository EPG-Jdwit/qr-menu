package com.joshua.qrmenu.util;

import com.joshua.qrmenu.models.mappers.Mapper;
import com.joshua.qrmenu.services.util.AbstractService;

public abstract class AbstractEnvironment<ServiceT extends AbstractService, MapperT extends Mapper<?, ?, ?>> {

    public abstract ServiceT initService();

    public abstract MapperT initMapper();

}
