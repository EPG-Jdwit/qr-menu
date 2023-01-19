package com.joshua.qrmenu.member;

import com.joshua.qrmenu.models.mappers.CategoryMapper;
import com.joshua.qrmenu.models.mappers.ProductMapper;
import com.joshua.qrmenu.services.MemberService;
import com.joshua.qrmenu.util.AbstractEnvironment;
import com.joshua.qrmenu.util.mocker.repositories.CategoryRepositoryMocker;
import com.joshua.qrmenu.util.mocker.repositories.ProductRepositoryMocker;

//TODO : ProductMapper doesn't belong here
public class MemberEnvironment extends AbstractEnvironment<MemberService, ProductMapper> {
    @Override
    public MemberService initService() {
        return new MemberService(
                ProductRepositoryMocker.init(),
                CategoryRepositoryMocker.init(),
                new ProductMapper(),
                new CategoryMapper()
        );
    }

    @Override
    public ProductMapper initMapper() {
        return null;
    }
}
