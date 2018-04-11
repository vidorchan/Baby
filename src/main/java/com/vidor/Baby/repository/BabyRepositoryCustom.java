package com.vidor.Baby.repository;

import java.util.List;

public interface BabyRepositoryCustom{

    public String sayHello();

    //基于原生态的sql进行查询
    List<Object[]> groupByBabyAsSql();
    //基于Hibernate的HQL进行查询
    List<Object[]> groupByBabyAsHql();
    //基于Specification的方式进行查询，使用的是CriteriaQuery进行查询
    List<Object[]> groupBybabyAsSpecification(Integer number);
}
