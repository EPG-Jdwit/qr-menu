package com.joshua.qrmenu.util;

import com.joshua.qrmenu.services.util.AbstractService;

public abstract class AbstractEnvironment<ServiceT extends AbstractService> {

    public abstract ServiceT initService();
}
